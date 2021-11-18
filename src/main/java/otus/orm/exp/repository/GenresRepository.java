package otus.orm.exp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import otus.orm.exp.entity.Genre;

public interface GenresRepository extends MongoRepository<Genre, String> {

}
