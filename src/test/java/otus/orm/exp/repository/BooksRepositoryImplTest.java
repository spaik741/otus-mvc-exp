package otus.orm.exp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import otus.orm.exp.entity.Author;
import otus.orm.exp.entity.Book;
import otus.orm.exp.entity.Genre;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
class BooksRepositoryImplTest {

    private static final String BOOK_NAME = "Nothing";
    private static final String FIRST = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";
    private static final String FOUR = "4";
    private static final String BOOK_NAME_2 = "Shine";

    @Autowired
    private BooksRepository repository;

    @Test
    public void getBookTest() {
        StepVerifier
                .create(repository.findById(THREE))
                .assertNext(b -> assertEquals(BOOK_NAME_2, b.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void getAllBooksTest() {
        StepVerifier
                .create(repository.findAll())
                .assertNext(b -> assertNotNull(b.getId()))
                .assertNext(b -> assertNotNull(b.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    public void saveBookTest() {

        Book book = new Book(FOUR, BOOK_NAME, new Author(FIRST, "a", "b"), new Genre(FIRST, "b"));
        StepVerifier
                .create(repository.save(book))
                .assertNext(b -> assertEquals(BOOK_NAME, b.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void updateBookTest() {
        Book book = new Book(FIRST, BOOK_NAME, new Author(FIRST, "a", "b"), new Genre(FIRST, "b"));
        StepVerifier
                .create(repository.save(book))
                .assertNext(b -> assertEquals(BOOK_NAME, b.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void deleteBookTest() {
        StepVerifier
                .create(repository.deleteById(TWO))
                .expectComplete()
                .verify();
        StepVerifier
                .create(repository.findAll())
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

}