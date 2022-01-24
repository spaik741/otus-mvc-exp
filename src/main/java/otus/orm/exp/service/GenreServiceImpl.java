package otus.orm.exp.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.orm.exp.repository.GenresRepository;
import otus.orm.exp.entity.Genre;

import java.util.*;

@Service
public class GenreServiceImpl implements GenresService {

    private final GenresRepository repository;

    public GenreServiceImpl(GenresRepository repository) {
        this.repository = repository;
    }

    @Override
    @HystrixCommand(commandKey = "genres", fallbackMethod = "findDefaultGenre")
    public List<Genre> getAllGenres() {
        List<Genre> genres = repository.findAll();
        return CollectionUtils.isEmpty(genres) ? new ArrayList<>() : genres;
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        return getAllGenres().stream().filter(b -> b.getId() == id).findFirst();
    }

    @Override
    @Transactional
    public void deleteGenre(long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Genre> saveGenre(Genre genre) {
        return Optional.of(repository.save(genre));
    }

    public  List<Genre> findDefaultGenre() {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setGenre("Default genre book");
        return Collections.singletonList(genre);
    }
}
