package hello.vuetilserver.controller.exception;

public class PostsNotMatchException extends RuntimeException{
    public PostsNotMatchException(String message) {
        super(message);
    }

    public PostsNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostsNotMatchException(Throwable cause) {
        super(cause);
    }
}
