package otus.orm.exp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otus.orm.exp.entity.Genre;
import otus.orm.exp.response.MessageResponse;
import otus.orm.exp.service.GenresService;

import java.util.List;
import java.util.Optional;

@RestController
public class GenreController {

    private final GenresService genresService;


    public GenreController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") long id) {
        return genresService.getGenreById(id)
                .map(g -> new ResponseEntity<Object>(g, HttpStatus.OK))
                .orElse(new ResponseEntity<>(new MessageResponse(String.format("Not found genre on id : %s", id)),
                        HttpStatus.OK));
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAll() {
        List<Genre> genres = genresService.getAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

}
