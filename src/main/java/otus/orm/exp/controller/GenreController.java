package otus.orm.exp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import otus.orm.exp.entity.Genre;
import otus.orm.exp.repository.GenresRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class GenreController {

    private final GenresRepository genresRepository;


    public GenreController(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @GetMapping("/genres/{id}")
    public Mono<ResponseEntity<Genre>> getBook(@PathVariable("id") String id) {
        return genresRepository.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/genres")
    public Flux<Genre> getAll() {
        return genresRepository.findAll();
    }

}
