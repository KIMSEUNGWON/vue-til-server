package hello.vuetilserver.repository;

import hello.vuetilserver.domain.Friends;
import hello.vuetilserver.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, Long> {

    @Query("select f from Friends f left join fetch f.friendingMemberId where f.friendingMemberId = :searchMember")
    Optional<List<Friends>> findAll(@Param("searchMember") Member searchMember);

    Optional<Friends> findFriendsByFriendedMemberIdAndFriendingMemberId(Long friendedMemberId, Member friendingMemberId);
}
