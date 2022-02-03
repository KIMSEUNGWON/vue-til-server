package hello.vuetilserver.controller.exception;

public class MemberChangePasswordNotMatchException extends RuntimeException{
    public MemberChangePasswordNotMatchException(String message) {
        super(message);
    }

    public MemberChangePasswordNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberChangePasswordNotMatchException(Throwable cause) {
        super(cause);
    }
}
