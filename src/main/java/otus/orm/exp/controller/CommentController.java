package otus.orm.exp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import otus.orm.exp.entity.Comment;
import otus.orm.exp.repository.CommentsRepository;
import reactor.core.publisher.Flux;

@RestController
public class CommentController {

    private final CommentsRepository commentsRepository;


    public CommentController(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

//    @GetMapping("/comments/{idBook}")
//    public Flux<Comment> getAll(@PathVariable("idBook") String idBook) {
//        return commentsRepository.findAllByBookId(idBook);
//    }

}
