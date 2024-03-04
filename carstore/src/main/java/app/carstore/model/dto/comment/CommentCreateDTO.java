package app.carstore.model.dto.comment;

public class CommentCreateDTO {

    private String userName;
    private Long offerId;
    private String comment;

    public CommentCreateDTO(String userName, Long offerId, String comment) {
        this.userName = userName;
        this.offerId = offerId;
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public CommentCreateDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Long getOfferId() {
        return offerId;
    }

    public CommentCreateDTO setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommentCreateDTO setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
