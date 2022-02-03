package hello.vuetilserver.controller.exception;

public class MemberNotExistException extends RuntimeException{
    public MemberNotExistException(String message) {
        super(message);
    }

    public MemberNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotExistException(Throwable cause) {
        super(cause);
    }
}
