package hello.vuetilserver.controller.exception;

public class ChatRoomsNotExistException extends RuntimeException{
    public ChatRoomsNotExistException(String message) {
        super(message);
    }

    public ChatRoomsNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatRoomsNotExistException(Throwable cause) {
        super(cause);
    }
}
