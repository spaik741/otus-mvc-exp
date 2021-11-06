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
@RequestMapping("book")
public class BookController {

    private final BooksService booksService;

    public BookController(BooksService booksService) {
        this.booksService = booksService;
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody Book book) {
        Optional<Book> bookOptional = booksService.saveBook(book);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Not save book"), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = booksService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBook(@PathVariable("id") long id) {
        Optional<Book> bookOptional = booksService.getBookById(id);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse(String.format("Not found book on id : %s", id)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteBook(@PathVariable("id") long id) {
        try {
            booksService.deleteBook(id);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse(String.format("Not found book on id : %s", id)), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse(String.format("Book on id : %s is deleted", id)), HttpStatus.OK);
    }

}
