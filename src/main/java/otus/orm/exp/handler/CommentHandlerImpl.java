package otus.orm.exp.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.orm.exp.entity.Book;
import otus.orm.exp.entity.Comment;
import otus.orm.exp.repository.CommentsRepository;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class CommentHandlerImpl implements CommentHandler{

    private final CommentsRepository repository;

    public CommentHandlerImpl(CommentsRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> byBookId(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAllByBookId(request.pathVariable("idBook")), Comment.class);
    }

}
