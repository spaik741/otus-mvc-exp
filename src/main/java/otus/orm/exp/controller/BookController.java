package otus.orm.exp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otus.orm.exp.entity.Book;
import otus.orm.exp.response.MessageResponse;
import otus.orm.exp.service.BooksService;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BooksService booksService;

    public BookController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping("/books")
    public ResponseEntity<?> save(@RequestBody Book book) {
        return booksService.saveBook(book)
                .map(a -> new ResponseEntity<Object>(a, HttpStatus.OK))
                .orElse(new ResponseEntity<>(new MessageResponse("Not save book"), HttpStatus.OK));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = booksService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> getBook(@PathVariable("id") long id) {
        return booksService.getBookById(id)
                .map(b -> new ResponseEntity<Object>(b, HttpStatus.OK))
                .orElse(new ResponseEntity<>(new MessageResponse(String.format("Not found book on id : %s", id)),
                        HttpStatus.OK));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<MessageResponse> deleteBook(@PathVariable("id") long id) {
        try {
            booksService.deleteBook(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(String.format("Not found book on id : %s", id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse(String.format("Book on id : %s is deleted", id)), HttpStatus.OK);
    }

}
