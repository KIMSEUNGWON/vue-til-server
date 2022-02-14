package hello.vuetilserver.controller.exception;

public class ChatRoomsEnterNotExistException extends RuntimeException{
    public ChatRoomsEnterNotExistException(String message) {
        super(message);
    }

    public ChatRoomsEnterNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatRoomsEnterNotExistException(Throwable cause) {
        super(cause);
    }
}
