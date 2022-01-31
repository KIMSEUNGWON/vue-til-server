package hello.vuetilserver.controller;

import hello.vuetilserver.api.Result;
import hello.vuetilserver.controller.exception.*;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Posts;
import hello.vuetilserver.domain.dto.PostsEditDto;
import hello.vuetilserver.domain.dto.PostsFindDto;
import hello.vuetilserver.domain.dto.PostsSaveDto;
import hello.vuetilserver.domain.dto.PostsSaveResultDto;
import hello.vuetilserver.service.MemberService;
import hello.vuetilserver.service.PostsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;
    private final MemberService memberService;

    @GetMapping("/")
    public Result posts(@RequestHeader("Authorization") String authorization) throws Exception {
        log.info("authorization = " + authorization);

        if (authorization == "") {
            log.info("인증 에러 발생");
            throw new UnAuthorizedAccessException("인증되지 않은 사용자의 접근");
        }

        return new Result(postsService.postsList());
    }

    @PostMapping(value = "/")
    public PostsSaveResultDto createOnePost(@RequestBody PostsSaveDto postsSaveDto, @RequestHeader("Authorization") String authorization) {
        log.info("authorization = " + authorization);
        log.info("postsSaveDto = " + postsSaveDto.getTitle());

        Member member = memberService.findMemberByToken(authorization);
        if (postsService.lengthExceedContents(postsSaveDto)) {
            log.info("게시글은 200자를 넘기면 안됩니다");
            throw new PostsLengthContentException("게시글은 글자 수 200자를 넘기면 안됩니다");
        }
        if (postsService.duplicatedTitle(postsSaveDto)) {
            log.info("중복된 게시글 제목 오류");
            throw new PostsDuplicatedTitleException("이미 존재하는 게시글 제목입니다.");
        }
        Posts savedPosts = postsService.save(postsSaveDto, member);
        PostsSaveResultDto postsSaveResultDto = new PostsSaveResultDto(savedPosts);

        return postsSaveResultDto;
    }

    @DeleteMapping("/{id}")
    public String deleteOnePosts(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization) {
        log.info("posts id = " + id);
        log.info("authorization = " + authorization);

        postsService.deleteOnePosts(id, authorization);

        return "success";
    }

    @GetMapping("/{id}")
    public PostsFindDto getOnePosts(@PathVariable String id, @RequestHeader("Authorization") String authorization) {
        log.info("posts id = " + id);
        log.info("authorization = " + authorization);

        PostsFindDto result = postsService.getOnePosts(id, authorization);

        return result;
    }

    //https://cheese10yun.github.io/spring-jpa-best-06/
    @PutMapping("/{id}")
    public PostsEditDto editOnePosts(@PathVariable String id, @RequestBody PostsEditDto postsEditDto, @RequestHeader("Authorization") String authorization) {
        log.info("posts id = " + id);
        log.info("authorization = " + authorization);
        log.info("postsEdit Dto = " + postsEditDto.getTitle() + ", " + postsEditDto.getContents());

        PostsEditDto result = postsService.editOnePosts(id, postsEditDto, authorization);

        return result;
    }

}
