package hello.vuetilserver.controller.exception;

public class MemberPasswordNotMatchException extends RuntimeException{
    public MemberPasswordNotMatchException(String message) {
        super(message);
    }

    public MemberPasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberPasswordNotMatchException(Throwable cause) {
        super(cause);
    }
}
