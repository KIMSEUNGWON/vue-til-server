package hello.vuetilserver.service;

import hello.vuetilserver.controller.exception.PostsNotFoundedException;
import hello.vuetilserver.controller.exception.PostsNotMatchException;
import hello.vuetilserver.controller.exception.UnAuthorizedAccessException;
import hello.vuetilserver.domain.Member;
import hello.vuetilserver.domain.Posts;
import hello.vuetilserver.domain.dto.PostsEditDto;
import hello.vuetilserver.domain.dto.PostsFindDto;
import hello.vuetilserver.domain.dto.PostsSaveDto;
import hello.vuetilserver.domain.dto.PostsSaveResultDto;
import hello.vuetilserver.repository.MemberRepository;
import hello.vuetilserver.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostsRepository postsRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Posts save(PostsSaveDto postsSaveDto, Member member) {
        Posts posts = new Posts(postsSaveDto, member);
        log.info("게시글 한 개 저장 메소드 실행");
        postsRepository.save(posts);
        return posts;
    }

    public List<PostsSaveResultDto> postsList() {
        log.info("모든 게시글 조회 메서드 실행");
        List<Posts> allPosts = postsRepository.findAll();
        List<PostsSaveResultDto> result = allPosts.stream()
                .map(post -> new PostsSaveResultDto(post.getId(), post.getTitle(), post.getContents(),
                        post.getCreatedBy().getUsername(), post.getCreatedAt(), post.getUpdatedAt()))
                .collect(Collectors.toList());
        return result;
    }

    @Transactional
    public Posts deleteOnePosts(String postsId, String authorization) {
        log.info("게시글 한 개 삭제 메서드 실행");
        log.info("posts id = " + postsId);
        log.info("authorization = " + authorization);

        if (authorization == "") {
            log.info("인증 에러 발생");
            throw new UnAuthorizedAccessException("인증되지 않은 사용자의 접근");
        }

        //https://krksap.tistory.com/1515
        Posts deletedPosts = postsRepository.findById(Long.parseLong(postsId)).orElseThrow(() -> new PostsNotFoundedException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findMemberByToken(authorization).orElseThrow(() -> new UnAuthorizedAccessException("인증되지 않은 사용자의 접근입니다."));

        if (!deletedPosts.getCreatedBy().equals(member)) {
            log.info("작성자와 삭제자가 다릅니다.");
            throw new PostsNotMatchException("작성자와 삭제자가 다릅니다.");
        }

        postsRepository.deleteById(Long.parseLong(postsId));

        return deletedPosts;
    }

    public PostsFindDto getOnePosts(String postsId, String authorization) {
        log.info("게시글 한 개 조회 메서드 실행");

        Posts foundedPosts = postsRepository.findById(Long.parseLong(postsId)).orElseThrow(() -> new PostsNotFoundedException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findMemberByToken(authorization).orElseThrow(() -> new UnAuthorizedAccessException("인증되지 않은 사용자의 접근입니다."));

        if (!foundedPosts.getCreatedBy().equals(member)) {
            log.info("작성자와 수정자가 다릅니다.");
            throw new PostsNotMatchException("작성자와 수정자가 다릅니다.");
        }

        PostsFindDto postsFindDto = new PostsFindDto(foundedPosts);

        return postsFindDto;
    }

    @Transactional
    public PostsEditDto editOnePosts(String postsId, PostsEditDto postsEditDto, String authorization) {
        log.info("게시글 한 개 수정 메서드 실행");

        Posts foundedPosts = postsRepository.findById(Long.parseLong(postsId)).orElseThrow(() -> new PostsNotFoundedException("게시글을 찾을 수 없습니다."));
        Member member = memberRepository.findMemberByToken(authorization).orElseThrow(() -> new UnAuthorizedAccessException("인증되지 않은 사용자의 접근입니다."));

        if (!foundedPosts.getCreatedBy().equals(member)) {
            log.info("작성자와 수정자가 다릅니다.");
            throw new PostsNotMatchException("작성자와 수정자가 다릅니다.");
        }

        foundedPosts.updateTitleAndContents(postsEditDto);

        PostsEditDto result = new PostsEditDto(foundedPosts);

        return result;
    }

    public boolean duplicatedTitle(PostsSaveDto postsSaveDto) {
        if (postsRepository.findPostsByTitle(postsSaveDto.getTitle()).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean lengthExceedContents(PostsSaveDto postsSaveDto) {
        if (postsSaveDto.getContents().length() > 200) {
            return true;
        }
        return false;
    }
}
