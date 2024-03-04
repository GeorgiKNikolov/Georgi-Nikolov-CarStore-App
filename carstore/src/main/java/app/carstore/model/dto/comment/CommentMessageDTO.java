package app.carstore.model.dto.comment;

public class CommentMessageDTO {

    private String message;

    public CommentMessageDTO() {}

    public CommentMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public CommentMessageDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
