package otus.orm.exp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import otus.orm.exp.entity.Author;
import otus.orm.exp.repository.AuthorsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class AuthorController {

    private final AuthorsRepository authorsRepository;

    public AuthorController(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }


    @GetMapping("/authors/{id}")
    public Mono<ResponseEntity<Author>> getAuthor(@PathVariable("id") String id) {
        return authorsRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/authors")
    public Flux<Author> getAll() {
        return authorsRepository.findAll();
    }

}
