package otus.orm.exp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import otus.orm.exp.entity.Comment;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CommentsRepository extends ReactiveMongoRepository<Comment, String> {

    Flux<Comment> findAllByBookId(String idBook);

    void deleteByBookId(String idBook);
}
