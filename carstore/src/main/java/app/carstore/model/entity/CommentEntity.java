package app.carstore.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class CommentEntity extends BaseEntity{

    private boolean approved;

    private LocalDateTime created;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne
    private OfferEntity offer;

    @ManyToOne
    private UserEntity user;

    public boolean isApproved() {
        return approved;
    }

    public CommentEntity setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public CommentEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommentEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public OfferEntity getOffer() {
        return offer;
    }

    public CommentEntity setOffer(OfferEntity offer) {
        this.offer = offer;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CommentEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
