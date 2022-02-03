package hello.vuetilserver.controller.exception;

public class CAuthenticationEntryPointException extends RuntimeException{
    public CAuthenticationEntryPointException(String message) {
        super(message);
    }

    public CAuthenticationEntryPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public CAuthenticationEntryPointException() {
        super();
    }
}
