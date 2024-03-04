package app.carstore.web.rest;

import app.carstore.model.dto.comment.CommentCreateDTO;
import app.carstore.model.dto.comment.CommentMessageDTO;
import app.carstore.model.view.CommentDisplayView;
import app.carstore.service.CommentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {

    private CommentsService commentsService;

    public CommentRestController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDisplayView>> getComments(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentsService.getAllCommentsForRoute(id));
    }


    @PostMapping(value = "/{id}/comments",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<CommentDisplayView> createComment(@PathVariable("id") Long id,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            @RequestBody CommentMessageDTO commentMessageDTO) {


        CommentCreateDTO comment = new CommentCreateDTO(
                userDetails.getUsername(),
                id,
                commentMessageDTO.getMessage()
        );

        CommentDisplayView newComment = commentsService.createComment(comment);

        return ResponseEntity.created(URI.create(String.format("/api/%d/comments/%d", id, newComment.getId())))
                .body(newComment);

    }

}
