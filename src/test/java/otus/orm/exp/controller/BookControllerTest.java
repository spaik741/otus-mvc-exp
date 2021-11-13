package otus.orm.exp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import otus.orm.exp.entity.Author;
import otus.orm.exp.entity.Book;
import otus.orm.exp.entity.Genre;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
class BookControllerTest {

    private static final String BOOKS_API = "/books";
    private static final String BOOKS_ID_API = "/books/{id}";

    @Autowired
    private WebApplicationContext webApplicationContext;


    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getAllBooksTest() throws Exception {
        mockMvc.perform(get(BOOKS_API))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getBookIdTest() throws Exception {
        mockMvc.perform(get(BOOKS_ID_API, 2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andReturn();
    }

    @Test
    public void saveBookTest() throws Exception {
        Book book = new Book(4L, "BOOK_NAME", new Author(2L, "a", "b"), new Genre(2L, "b"));
        mockMvc.perform(post(BOOKS_API).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(book)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("BOOK_NAME")))
                .andReturn();
    }

    @Test
    public void deleteBookIdTest() throws Exception {
        mockMvc.perform(delete(BOOKS_ID_API, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("Book on id : 1 is deleted")));
    }
}