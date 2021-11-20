package otus.orm.exp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.orm.exp.entity.Author;
import reactor.core.publisher.Mono;

public interface AuthorsRepository extends ReactiveMongoRepository<Author, String> {
    Mono<Author> findById(String id);
}
