package hello.vuetilserver.controller.exception;

public class PostsNotFoundedException extends RuntimeException{
    public PostsNotFoundedException(String message) {
        super(message);
    }

    public PostsNotFoundedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostsNotFoundedException(Throwable cause) {
        super(cause);
    }
}
