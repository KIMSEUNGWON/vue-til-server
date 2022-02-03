package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.PostsDuplicatedTitleException;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Posts;
import hello.vuetilserver.domain.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostsServiceTest {

    @Autowired
    PostsService postsService;
    @Autowired
    MemberService memberService;

    @Test
    void saveTest() {
        //given
        MemberLoginDto memberLoginDto = new MemberLoginDto("a@a.com", "123");
        Member member = memberService.findMember(memberLoginDto);
//        PostsSaveDto postsSaveDto = new PostsSaveDto("title1", "contents");
//        PostsSaveDto postsSaveDto = new PostsSaveDto("title1", "contents");
        PostsSaveDto postsSaveDto = new PostsSaveDto("title", "contents");

        //when
        Posts posts = postsService.save(postsSaveDto, member);

        //then
//        assertThrows(PostsDuplicatedTitleException.class, () -> {
//            postsService.save(postsSaveDto, member);
//        });
        assertThat(posts.getTitle()).isEqualTo(postsSaveDto.getTitle());
        assertThat(posts.getCreatedBy()).isEqualTo(member);
    }

    @Test
    void postsListTest() {
        //given
        List<PostsSaveResultDto> postsSaveResultDtoList = postsService.postsList();

        //when

        //then
        assertThat(postsSaveResultDtoList.get(0).getTitle()).isEqualTo("title1");
        assertThat(postsSaveResultDtoList.get(1).getTitle()).isEqualTo("title2");
    }

    @Test
    void deleteTest() {
        //given
        MemberLoginDto memberLoginDto = new MemberLoginDto("a@a.com", "123");
        Member member = memberService.findMember(memberLoginDto);
        String postsId = "3";

        //when
        Posts posts = postsService.deleteOnePosts(postsId, member);

        //then
        assertThat(posts.getCreatedBy()).isEqualTo(member);
        assertThat(posts.getTitle()).isEqualTo("title1");
    }

    @Test
    void getOnePostsTest() {
        //given
        MemberLoginDto memberLoginDto = new MemberLoginDto("a@a.com", "123");
        Member member = memberService.findMember(memberLoginDto);
        String postsId = "3";

        //when
        PostsFindDto postsFindDto = postsService.getOnePosts(postsId, member);

        //then
        assertThat(postsFindDto.getTitle()).isEqualTo("title1");
        assertThat(postsFindDto.getContents()).isEqualTo("contents1");
    }

    @Test
    void editOnePostsTest() {
        //given
        MemberLoginDto memberLoginDto = new MemberLoginDto("a@a.com", "123");
        Member member = memberService.findMember(memberLoginDto);
        PostsEditDto postsEditDto = new PostsEditDto("editTitle", "editContents");
        String postsId = "3";

        //when
        postsService.editOnePosts(postsId, postsEditDto, member);
        PostsFindDto postsFindDto = postsService.getOnePosts(postsId, member);

        //then
        assertThat(postsFindDto.getTitle()).isEqualTo(postsEditDto.getTitle());
        assertThat(postsFindDto.getContents()).isEqualTo(postsEditDto.getContents());
    }
}