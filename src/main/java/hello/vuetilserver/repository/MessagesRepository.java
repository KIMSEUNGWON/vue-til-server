package hello.vuetilserver.repository;

import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessagesRepository extends JpaRepository<Messages, Long> {

    @Query("select m from Messages m left join fetch m.senderId where m.senderId = :searchMember")
    Optional<List<Messages>> findAll(@Param("searchMember") Member searchMember);

    @Query(value = "select m from Messages m where m.receiverId = :receiverId")
    Optional<List<Messages>> findAll(@Param("receiverId") Long receiverId);
}
