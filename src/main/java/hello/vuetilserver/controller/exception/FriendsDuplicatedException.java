package hello.vuetilserver.controller.exception;

public class FriendsDuplicatedException extends RuntimeException{
    public FriendsDuplicatedException(String message) {
        super(message);
    }

    public FriendsDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendsDuplicatedException(Throwable cause) {
        super(cause);
    }
}
