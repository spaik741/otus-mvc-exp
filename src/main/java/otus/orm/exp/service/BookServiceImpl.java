package otus.orm.exp.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.orm.exp.repository.BooksRepository;
import otus.orm.exp.entity.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BooksService{

    private final BooksRepository repository;

    public BookServiceImpl(BooksRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "books", fallbackMethod = "findDefaultBooks")
    public List<Book> getAllBooks() {
        List<Book> books = repository.findAll();
        return CollectionUtils.isEmpty(books)? new ArrayList<>(): books;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getBookById(long id) {
        return getAllBooks().stream().filter(b -> b.getId() == id).findFirst();
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Book> saveBook(Book book) {
        return Optional.of(repository.save(book));
    }

    public List<Book> findDefaultBooks() {
        Book book = new Book();
        book.setId(1L);
        book.setName("Default book");
        return Collections.singletonList(book);
    }

}
