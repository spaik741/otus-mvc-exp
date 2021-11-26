package otus.orm.exp.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.orm.exp.entity.Book;
import otus.orm.exp.repository.BooksRepository;
import otus.orm.exp.repository.CommentsRepository;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class BookHandlerImpl implements BookHandler {

    private final BooksRepository booksRepository;
    private final CommentsRepository commentsRepository;


    public BookHandlerImpl(BooksRepository booksRepository, CommentsRepository commentsRepository) {
        this.booksRepository = booksRepository;
        this.commentsRepository = commentsRepository;
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(booksRepository.findAll(), Book.class);
    }

    public Mono<ServerResponse> byId(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(booksRepository.findById(request.pathVariable("id")), Book.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String idBook = request.pathVariable("id");
        return noContent().build(booksRepository.deleteById(idBook).then(commentsRepository.deleteByBookId(idBook)));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Book.class).flatMap(booksRepository::save).flatMap(b -> created(request.uriBuilder().pathSegment(b.getId()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(b)));
    }

}
