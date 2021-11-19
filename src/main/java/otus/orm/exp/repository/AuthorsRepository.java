package otus.orm.exp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.orm.exp.entity.Author;

public interface AuthorsRepository extends MongoRepository<Author, String> {

}
