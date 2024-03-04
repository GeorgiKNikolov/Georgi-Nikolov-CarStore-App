package app.carstore.model.view;

public class CommentDisplayView {
    private Long id;
    private String authorName;
    private String comment;

    public CommentDisplayView(){}

    public CommentDisplayView(Long id, String authorName, String comment) {
        this.id = id;
        this.authorName = authorName;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public CommentDisplayView setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return authorName;
    }

    public CommentDisplayView setUserName(String userName) {
        this.authorName = userName;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommentDisplayView setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
