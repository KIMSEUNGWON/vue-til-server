package hello.vuetilserver.controller.exception;

public class MessageNotExistException extends RuntimeException{
    public MessageNotExistException(String message) {
        super(message);
    }

    public MessageNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageNotExistException(Throwable cause) {
        super(cause);
    }
}
