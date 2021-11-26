package otus.orm.exp.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.orm.exp.entity.Author;
import otus.orm.exp.entity.Book;
import otus.orm.exp.entity.Genre;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookHandlerImplTest {

    private static final String BOOKS_API = "/books";
    private static final String BOOK_NAME = "NAME";
    private static final String JSON_NAME = "name";
    private static final String JSON_ID = "id";


    private WebTestClient client;
    @Autowired
    private RouterFunction<ServerResponse> route;

    @BeforeEach
    public void setup() throws Exception {
        this.client = WebTestClient
                .bindToRouterFunction(route)
                .build();
    }


    @Test
    public void getBookIdTest() throws Exception {
        client.get()
                .uri(BOOKS_API + "/3")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath(JSON_ID)
                .isNotEmpty()
                .jsonPath(JSON_NAME)
                .isEqualTo("Shine");
    }

    @Test
    public void getAllBooksTest() throws Exception {
        client.get()
                .uri(BOOKS_API)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void saveBookTest() throws Exception {
        Book book = new Book("4", BOOK_NAME, new Author("1", "a", "b"), new Genre("1", "b"));
        client.post()
                .uri(BOOKS_API)
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath(JSON_ID)
                .isNotEmpty()
                .jsonPath(JSON_NAME)
                .isEqualTo(BOOK_NAME);
    }

    @Test
    public void deleteBookIdTest() throws Exception {
        this.client.delete()
                .uri(BOOKS_API + "/{id}", "1")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

}