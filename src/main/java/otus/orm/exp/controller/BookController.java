package otus.orm.exp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import otus.orm.exp.entity.Author;
import otus.orm.exp.entity.Book;
import otus.orm.exp.entity.Genre;
import otus.orm.exp.service.AuthorsService;
import otus.orm.exp.service.BooksService;
import otus.orm.exp.service.GenresService;
import otus.orm.exp.service.factory.BookFactory;
import otus.orm.exp.service.io.IOService;

import java.util.List;
import java.util.Optional;

@RestController("/book")
public class BookController {

    private final BooksService booksService;
    private final AuthorsService authorsService;
    private final GenresService genresService;
    private final IOService ioService;
    private final BookFactory bookFactory;

    public BookController(BooksService booksService, AuthorsService authorsService, GenresService genresService, IOService ioService, BookFactory bookFactory) {
        this.booksService = booksService;
        this.authorsService = authorsService;
        this.genresService = genresService;
        this.ioService = ioService;
        this.bookFactory = bookFactory;
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute Book book) {
        booksService.saveBook(book);
        return "redirect:books";
    }

    @PostMapping("/create")
    public String create(@RequestParam("name") String name, @ModelAttribute Author author, @ModelAttribute Genre genre) {
        Optional<Book> book = bookFactory.createBook(name, author.getId(), genre.getId());
        if (book.isPresent()) {
            booksService.saveBook(book.get());
        }
        return "redirect:books";
    }

    @GetMapping("/edit")
    public String get(@RequestParam("id") long id, Model model) {
        Optional<Book> book = booksService.getBookById(id);
        if (book.isPresent()) {
            model.addAttribute("book", book.get());
            model.addAttribute("authors", authorsService.getAllAuthors());
            model.addAttribute("genres", genresService.getAllGenres());
        }
        return "book/edit";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        model.addAttribute("authors", authorsService.getAllAuthors());
        model.addAttribute("genres", genresService.getAllGenres());
        return "book/create";
    }

    @GetMapping("/books")
    public String getAll(Model model) {
        List<Book> books = booksService.getAllBooks();
        model.addAttribute("books", books);
        return "book/books";
    }

    @PostMapping("/books/delete")
    public String delete(@RequestParam("id") long id) {
        booksService.deleteBook(id);
        return "redirect:/books";
    }

}
