package otus.orm.exp.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import otus.orm.exp.entity.Book;
import otus.orm.exp.entity.Comment;
import otus.orm.exp.response.MessageResponse;
import otus.orm.exp.service.BooksService;
import otus.orm.exp.service.CommentService;
import otus.orm.exp.service.factory.CommentFactory;
import otus.orm.exp.service.io.IOService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final BooksService booksService;
    private final CommentFactory commentFactory;
    private final IOService ioService;

    public CommentController(CommentService commentService, BooksService booksService, CommentFactory commentFactory, IOService ioService) {
        this.commentService = commentService;
        this.booksService = booksService;
        this.commentFactory = commentFactory;
        this.ioService = ioService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestParam("message") String message,@RequestParam("id") long idBook) {
        Optional<Comment> comment = commentFactory.createComment(message, new Date(), idBook);
        Optional<Comment> commentOptional = commentService.saveComment(comment.get());
        if (commentOptional.isPresent()) {
            return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Not save comment"), HttpStatus.OK);
    }

    @GetMapping("all/{idBook}")
    public ResponseEntity<List<Comment>> getAll(@PathVariable("idBook") long idBook) {
        List<Comment> comments = commentService.getAllComments(idBook);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") long id) {
        try {
            commentService.deleteComment(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(String.format("Not found comment on id : %s", id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse(String.format("Comment on id : %s is deleted", id)), HttpStatus.OK);
    }

}
