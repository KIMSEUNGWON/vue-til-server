package hello.vuetilserver.repository;

import hello.vuetilserver.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findPostsByTitle(String title);
}
