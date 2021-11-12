package otus.orm.exp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.orm.exp.entity.Comment;
import otus.orm.exp.response.MessageResponse;
import otus.orm.exp.service.CommentService;
import otus.orm.exp.service.factory.CommentFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentFactory commentFactory;


    public CommentController(CommentService commentService, CommentFactory commentFactory) {
        this.commentService = commentService;
        this.commentFactory = commentFactory;
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<?> save(@RequestParam("message") String message, @PathVariable("id") long idBook) {
        Optional<Comment> comment = commentFactory.createComment(message, new Date(), idBook);
        Optional<Comment> commentOptional = commentService.saveComment(comment.get());
        if (commentOptional.isPresent()) {
            return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Not save comment"), HttpStatus.OK);
    }

    @GetMapping("/comments/{idBook}")
    public ResponseEntity<List<Comment>> getAll(@PathVariable("idBook") long idBook) {
        List<Comment> comments = commentService.getAllComments(idBook);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") long id) {
        try {
            commentService.deleteComment(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(String.format("Not found comment on id : %s", id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse(String.format("Comment on id : %s is deleted", id)), HttpStatus.OK);
    }

}
