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

public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

    @Query("select cr from ChatRooms cr left join fetch cr.chatRoomsEnterList cre where cre.member = :searchMember")
    Optional<List<ChatRooms>> findAll(@Param("searchMember") Member member, Pageable pageable);

    @Query("select cr from ChatRooms cr left join fetch cr.chatRoomsEnterList cre")
    Optional<List<ChatRooms>> findAllByPaging(Pageable pageable);

    @Query("select cr from ChatRooms cr left join fetch cr.chatRoomsEnterList cre where cre.member = :searchMember and cr.id = :chatRoomsId")
    Optional<ChatRooms> findByIdAndChatRoomsEnterListContains(@Param("chatRoomsId") Long chatRoomsId, @Param("searchMember") Member searchMember);
}
