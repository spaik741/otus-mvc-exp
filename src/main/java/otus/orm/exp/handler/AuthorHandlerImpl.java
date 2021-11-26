package otus.orm.exp.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.orm.exp.entity.Author;
import otus.orm.exp.entity.Book;
import otus.orm.exp.repository.AuthorsRepository;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class AuthorHandlerImpl implements AuthorHandler {

    private final AuthorsRepository repository;

    public AuthorHandlerImpl(AuthorsRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(repository.findAll(), Author.class);
    }
}
