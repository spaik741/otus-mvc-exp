package otus.orm.exp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.orm.exp.entity.Author;
import otus.orm.exp.entity.Genre;
import reactor.core.publisher.Mono;

public interface GenresRepository extends ReactiveMongoRepository<Genre, String> {
    Mono<Genre> findById(String id);

}
