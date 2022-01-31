package hello.vuetilserver.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

//https://sdy-study.tistory.com/241
@ControllerAdvice
public class PostsExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PostsErrorResponse> handleException(UnAuthorizedAccessException e) {
        PostsErrorResponse error = new PostsErrorResponse();

        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setMessage("인증되지 않은 사용자의 접근입니다, 로그인을 해주세요");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<PostsErrorResponse> handleException(PostsDuplicatedTitleException e) {
        PostsErrorResponse error = new PostsErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("중복된 제목입니다. 다른 제목을 입력해주세요.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // body, status code
    }

    @ExceptionHandler
    public ResponseEntity<PostsErrorResponse> handleException(PostsNotFoundedException e) {
        PostsErrorResponse error = new PostsErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage("게시글을 찾을 수 없습니다.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PostsErrorResponse> handleException(PostsNotMatchException e) {
        PostsErrorResponse error = new PostsErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("게시글 작성자와 게시글을 조회 또는 삭제하려는 사용자가 다릅니다.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
