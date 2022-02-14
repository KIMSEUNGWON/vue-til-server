package hello.vuetilserver.controller.exception;

public class ChatRoomsDuplicatedParticipantException extends RuntimeException{
    public ChatRoomsDuplicatedParticipantException(String message) {
        super(message);
    }

    public ChatRoomsDuplicatedParticipantException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatRoomsDuplicatedParticipantException(Throwable cause) {
        super(cause);
    }
}
