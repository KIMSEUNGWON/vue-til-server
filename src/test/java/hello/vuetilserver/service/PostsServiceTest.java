package hello.vuetilserver.service;

import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Posts;
import hello.vuetilserver.domain.dto.MemberLoginDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostsServiceTest {

    @Autowired
    PostsService postsService;
    @Autowired
    MemberService memberService;

    @Test
    void deleteTest() {
        //given
        MemberLoginDto memberLoginDto = new MemberLoginDto("a@a.com", 123L);
        Member member = memberService.findMember(memberLoginDto);
        String postsId = "3";
        String authorization = member.getToken();

        //when
        Posts posts = postsService.deleteOnePosts(postsId, authorization);

        //then
        assertThat(posts.getCreatedBy()).isEqualTo(member);
        assertThat(posts.getTitle()).isEqualTo("title1");
    }
}