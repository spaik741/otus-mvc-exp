package otus.orm.exp.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.orm.exp.entity.Book;
import otus.orm.exp.repository.BooksRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class BookHandlerImpl implements BookHandler{

    private final BooksRepository repository;

    public BookHandlerImpl(BooksRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll(), Book.class);
    }

    public Mono<ServerResponse> byId(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(repository.findById(request.pathVariable("id")), Book.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return noContent().build(repository.deleteById(request.pathVariable("id")));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Book.class).flatMap(repository::save).flatMap(b -> created(request.uriBuilder().pathSegment(b.getId()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromValue(b)));
    }
}
