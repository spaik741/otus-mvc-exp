package otus.orm.exp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import otus.orm.exp.controller.BookController;
import otus.orm.exp.service.BooksService;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    private static final String BOOKS_API = "/books";
    private static final String BOOKS_ID_API = "/books/{id}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    public void getAllBooksTest() throws Exception {
        mockMvc.perform(get(BOOKS_API))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    public void deleteBookIdTest() throws Exception {
        mockMvc.perform(delete(BOOKS_ID_API, 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message", is("Book on id : 1 is deleted")));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    public void getBookIdTest() throws Exception {
        mockMvc.perform(get(BOOKS_ID_API, 2))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }
}
