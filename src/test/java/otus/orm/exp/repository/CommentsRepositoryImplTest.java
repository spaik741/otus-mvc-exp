package otus.orm.exp.repository;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import otus.orm.exp.entity.Author;
import otus.orm.exp.entity.Book;
import otus.orm.exp.entity.Comment;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentsRepositoryImplTest {

    @Autowired
    private CommentsRepository commentsRepository;
    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private TestEntityManager em;

    private static final String MESSAGE = "book not cool";
    private static final int LIST_SIZE_1 = 1;
    private static final int LIST_SIZE_2 = 4;
    private static final long FIRST = 1;
    private static final long COMMENT = 4;

    @Test
    public void getCommentTest() {
        Comment comment = commentsRepository.findById(FIRST).get();
        Comment expectedComment = em.find(Comment.class, FIRST);
        assertThat(comment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    public void getAllCommentTest() {
        assertThat(commentsRepository.findAllByBookId(FIRST)).hasSize(LIST_SIZE_1)
                .allMatch(c -> StringUtils.isNotBlank(c.getMessage()))
                .allMatch(c -> c.getMessageDate() != null)
                .allMatch(c -> c.getBook() != null);
    }

    @Test
    public void deleteCommentTest() {
        commentsRepository.deleteById(FIRST);
        assertThat(em.find(Comment.class, FIRST)).isNull();
    }

    @Test
    public void saveCommentTest() {
        Comment comment = commentsRepository.save(new Comment(COMMENT, MESSAGE, new Date(), booksRepository.getById(FIRST)));
        Comment expectedComment = em.find(Comment.class, COMMENT);
        assertEquals(LIST_SIZE_2, commentsRepository.findAll().size());
        assertThat(comment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    public void updateCommentTest() {
        Comment comment = commentsRepository.save(new Comment(FIRST, MESSAGE, new Date(), booksRepository.getById(FIRST)));
        Comment expectedComment = em.find(Comment.class, FIRST);
        assertEquals(MESSAGE, commentsRepository.findById(FIRST).get().getMessage());
        assertThat(comment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

}