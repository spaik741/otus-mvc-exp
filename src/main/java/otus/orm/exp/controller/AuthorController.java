package otus.orm.exp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import otus.orm.exp.entity.Author;
import otus.orm.exp.response.MessageResponse;
import otus.orm.exp.service.AuthorsService;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {

    private final AuthorsService authorsService;

    public AuthorController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }


    @GetMapping("/authors/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") String id) {
        return authorsService.getAuthorById(id)
                .map(a -> new ResponseEntity<Object>(a, HttpStatus.OK))
                .orElse(new ResponseEntity<>(new MessageResponse(String.format("Not found author on id : %s", id)),
                        HttpStatus.OK));
    }

    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAll() {
        List<Author> authors = authorsService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

}
