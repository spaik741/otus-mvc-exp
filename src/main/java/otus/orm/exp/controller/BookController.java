package otus.orm.exp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.orm.exp.entity.Book;
import otus.orm.exp.repository.BooksRepository;
import otus.orm.exp.response.MessageResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BookController {

    private final BooksRepository booksRepository;

    public BookController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @PostMapping("/books")
    public Mono<ResponseEntity<Book>> save(@RequestBody Book book) {
        return booksRepository.save(book)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/books")
    public Flux<Book> getAll() {
        return booksRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Mono<ResponseEntity<Book>> getBook(@PathVariable("id") String id) {
        return booksRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<MessageResponse> deleteBook(@PathVariable("id") String id) {
        try {
            booksRepository.deleteById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(String.format("Not found book on id : %s", id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse(String.format("Book on id : %s is deleted", id)), HttpStatus.OK);
    }

}
