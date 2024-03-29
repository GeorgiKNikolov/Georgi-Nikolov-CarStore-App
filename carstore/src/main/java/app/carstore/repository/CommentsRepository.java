package app.carstore.repository;

import app.carstore.model.entity.CommentEntity;
import app.carstore.model.entity.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity,Long> {
    Optional<List<CommentEntity>> findAllByOffer(OfferEntity offer);
}
