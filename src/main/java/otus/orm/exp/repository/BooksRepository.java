package otus.orm.exp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.orm.exp.entity.Book;
import reactor.core.publisher.Mono;

public interface BooksRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Book> findById(String id);

    Mono<Void> deleteById(String id);

}
