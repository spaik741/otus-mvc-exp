package otus.orm.exp.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.orm.exp.entity.Comment;
import otus.orm.exp.service.BooksService;
import otus.orm.exp.service.CommentService;
import otus.orm.exp.service.factory.CommentFactory;
import otus.orm.exp.service.io.IOService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentCommands {

    private final CommentService commentService;
    private final BooksService booksService;
    private final CommentFactory commentFactory;
    private final IOService ioService;

    public CommentCommands(CommentService commentService, BooksService booksService, CommentFactory commentFactory, IOService ioService) {
        this.commentService = commentService;
        this.booksService = booksService;
        this.commentFactory = commentFactory;
        this.ioService = ioService;
    }

    @RequestMapping
    public void save(String message, long idBook) {
        Optional<Comment> comment = commentFactory.createComment(message, new Date(), idBook);
        if (comment.isPresent()) {
            try {
                commentService.saveComment(comment.get());
                ioService.printString("Коммент сохранен. " + comment.get());
            } catch (Exception e) {
                ioService.printString("Коммент не сохранен. ");
            }
        }
    }

    @RequestMapping
    public void get(long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        String answerText = comment.map(value -> "Коммент получен. " + value).orElse("Коммент не получен.");
        ioService.printString(answerText);
    }

    @RequestMapping
    public void getAll(long idBook) {
        List<Comment> comments = commentService.getAllComments(idBook);
        if (CollectionUtils.isNotEmpty(comments)) {
            ioService.printString("Комменты получены. " + comments);
            return;

        }
        ioService.printString("Комменты не найдены.");
    }

    @RequestMapping
    public void delete(long id) {
        try {
            commentService.deleteComment(id);
            ioService.printString(String.format("Коммент № [%s] удален. ", id));
        } catch (Exception e) {
            ioService.printString("Коммент не удален. ");
        }
    }
}
