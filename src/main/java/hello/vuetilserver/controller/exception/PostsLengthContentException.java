package hello.vuetilserver.controller.exception;

public class PostsLengthContentException extends RuntimeException{
    public PostsLengthContentException(String message) {
        super(message);
    }

    public PostsLengthContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostsLengthContentException(Throwable cause) {
        super(cause);
    }
}
