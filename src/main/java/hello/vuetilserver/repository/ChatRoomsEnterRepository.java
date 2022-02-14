package hello.vuetilserver.repository;

import hello.vuetilserver.domain.ChatRooms;
import hello.vuetilserver.domain.ChatRoomsEnter;
import hello.vuetilserver.domain.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomsEnterRepository extends JpaRepository<ChatRoomsEnter, Long> {

    //https://bcp0109.tistory.com/304
//    @Query("select cr from ChatRoomsEnter cr left join fetch cr.member where cr.member = :searchMember")
//    Optional<List<ChatRoomsEnter>> findAll(@Param("searchMember") Member searMember, Pageable pageable);
    @Query("select cre from ChatRoomsEnter  cre left join fetch cre.member where cre.member = :searchMember")
    Optional<List<ChatRoomsEnter>> findAll(@Param("searchMember") Member member);

    List<ChatRoomsEnter> findAllByChatRooms(ChatRooms chatRooms);
}
