package app.carstore.service;

import app.carstore.model.dto.comment.CommentCreateDTO;
import app.carstore.model.entity.CommentEntity;
import app.carstore.model.entity.OfferEntity;
import app.carstore.model.entity.UserEntity;
import app.carstore.model.view.CommentDisplayView;
import app.carstore.repository.CommentsRepository;
import app.carstore.repository.OfferRepository;
import app.carstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsService {

    private OfferRepository offerRepository;
    private UserRepository userRepository;
    private CommentsRepository commentsRepository;

    public CommentsService(OfferRepository offerRepository,
                           UserRepository userRepository, CommentsRepository commentsRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.commentsRepository = commentsRepository;
    }

    public List<CommentDisplayView> getAllCommentsForRoute(Long id) {

        OfferEntity offer = offerRepository.findById(id).orElseThrow();

        List<CommentEntity> comments = commentsRepository.findAllByOffer(offer).get();
        return comments.stream().map(comment -> new CommentDisplayView(comment.getId(), comment.getUser().getFirstName() + " " + comment.getUser().getLastName(),
                comment.getComment())).collect(Collectors.toList());
    }

    public CommentDisplayView createComment(CommentCreateDTO commentDTO){

        UserEntity author = userRepository.findByEmail(commentDTO.getUserName()).get();

        CommentEntity comment = new CommentEntity();
        comment.setCreated(LocalDateTime.now());
        comment.setOffer(offerRepository.getReferenceById(commentDTO.getOfferId()));
        comment.setUser(author);
        comment.setApproved(true);
        comment.setComment(commentDTO.getComment());

        commentsRepository.save(comment);


        return new CommentDisplayView(comment.getId(), author.getLastName(), comment.getComment());

    }

}
