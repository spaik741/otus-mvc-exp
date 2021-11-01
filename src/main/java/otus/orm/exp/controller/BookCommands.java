package otus.orm.exp.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.orm.exp.entity.Book;
import otus.orm.exp.service.BooksService;
import otus.orm.exp.service.factory.BookFactory;
import otus.orm.exp.service.io.IOService;

import java.util.List;
import java.util.Optional;

@Controller
public class BookCommands {

    private final BooksService booksService;
    private final IOService ioService;
    private final BookFactory bookFactory;

    public BookCommands(BooksService booksService, IOService ioService, BookFactory bookFactory) {
        this.booksService = booksService;
        this.ioService = ioService;
        this.bookFactory = bookFactory;
    }

    @RequestMapping
    public void save(String name,
                     long idAuthor,
                     long idGenre) {
        Optional<Book> book = bookFactory.createBook(name, idAuthor, idGenre);
        if (book.isPresent()) {
            try {
                booksService.saveBook(book.get());
                ioService.printString("Книга сохранена. " + book.get());
            }catch (Exception e) {
                ioService.printString("Книга не сохранена. ");
            }
        }
    }

    @RequestMapping
    public void get(long id) {
        Optional<Book> book = booksService.getBookById(id);
        String answerText = book.map(value -> "Книга получена. " + value).orElse("Книга не получена.");
        ioService.printString(answerText);
    }

    @RequestMapping
    public void getAll() {
        List<Book> books = booksService.getAllBooks();
        if (CollectionUtils.isNotEmpty(books)) {
            ioService.printString("Книги получены. " + books);
        } else {
            ioService.printString("Книги не найдены.");
        }
    }

    @RequestMapping
    public void delete(long id) {
        try {
            booksService.deleteBook(id);
            ioService.printString(String.format("Книга № [%s] удалена. ", id));
        }catch (Exception e) {
            ioService.printString("Книга не удалена. ");
        }
    }

}
