package hello.vuetilserver.controller.exception;

public class PostsDuplicatedTitleException extends RuntimeException{
    public PostsDuplicatedTitleException(String message) {
        super(message);
    }

    public PostsDuplicatedTitleException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostsDuplicatedTitleException(Throwable cause) {
        super(cause);
    }
}
