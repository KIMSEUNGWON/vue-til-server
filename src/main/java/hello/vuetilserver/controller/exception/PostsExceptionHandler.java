package hello.vuetilserver.controller.exception;

import hello.vuetilserver.controller.CommonResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

//https://sdy-study.tistory.com/241
@ControllerAdvice
public class PostsExceptionHandler {

    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(UnAuthorizedAccessException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setMessage("인증되지 않은 사용자의 접근입니다, 로그인을 해주세요");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PostsDuplicatedTitleException.class)
    public ResponseEntity<ErrorResponse> handleException(PostsDuplicatedTitleException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("중복된 제목입니다. 다른 제목을 입력해주세요.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // body, status code
    }

    @ExceptionHandler(PostsNotFoundedException.class)
    public ResponseEntity<ErrorResponse> handleException(PostsNotFoundedException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage("게시글을 찾을 수 없습니다.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostsNotMatchException.class)
    public ResponseEntity<ErrorResponse> handleException(PostsNotMatchException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("게시글 작성자와 게시글을 조회 또는 삭제하려는 사용자가 다릅니다.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberNotExistException.class)
    public ResponseEntity<ErrorResponse> handleException(MemberNotExistException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("존재하지 않는 회원입니다. 회원 이름을 다시 확인해 주세요.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberPasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> handleException(MemberPasswordNotMatchException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("현재 비밀번호가 일치하지 않습니다.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberChangePasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> handleException(MemberChangePasswordNotMatchException e) {
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("바꾸려는 비밀번호가 일치하지 않습니다.");
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public ResponseEntity<CommonResponse> authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return new ResponseEntity<>(new CommonResponse(false, -1002, "해당 리소스에 접근하기 위한 권한이 없습니다.", LocalDateTime.now()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CommonResponse> AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return new ResponseEntity<>(new CommonResponse(false, -1003, "보유한 권한으로 접근할 수 없는 리소스 입니다.", LocalDateTime.now()), HttpStatus.UNAUTHORIZED);
    }
}
