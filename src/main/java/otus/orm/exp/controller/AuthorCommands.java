package otus.orm.exp.controller;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import otus.orm.exp.entity.Author;
import otus.orm.exp.service.AuthorsService;
import otus.orm.exp.service.io.IOService;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthorCommands {

    private final AuthorsService authorsService;
    private final IOService ioService;

    public AuthorCommands(AuthorsService authorsService, IOService ioService) {
        this.authorsService = authorsService;
        this.ioService = ioService;
    }

    @RequestMapping
    public void save(long id, String firstName, String lastName) {
        Author author = new Author(id, firstName, lastName);
        try {
            authorsService.saveAuthor(author);
            ioService.printString("Автор сохранен. " + author);
        } catch (Exception e) {
            ioService.printString("Автор не сохранен. ");
        }
    }

    @RequestMapping
    public void get(long id) {
        Optional<Author> author = authorsService.getAuthorById(id);
        String answerText = author.map(value -> "Автор получен. " + value).orElse("Автор не получен.");
        ioService.printString(answerText);
    }

    @RequestMapping
    public void getAll() {
        List<Author> authors = authorsService.getAllAuthors();
        if (CollectionUtils.isNotEmpty(authors)) {
            ioService.printString("Авторы получены. " + authors);
        } else {
            ioService.printString("Авторы не найдены.");
        }
    }

    @RequestMapping
    public void delete(long id) {
        try {
            authorsService.deleteAuthor(id);
            ioService.printString(String.format("Автор № [%s] удален. ", id));
        } catch (Exception e) {
            ioService.printString("Автор не удален. ");
        }
    }
}
