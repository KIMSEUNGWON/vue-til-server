package hello.vuetilserver.controller.exception;

public class FriendsNotExistException extends RuntimeException{
    public FriendsNotExistException(String message) {
        super(message);
    }

    public FriendsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendsNotExistException(Throwable cause) {
        super(cause);
    }
}
