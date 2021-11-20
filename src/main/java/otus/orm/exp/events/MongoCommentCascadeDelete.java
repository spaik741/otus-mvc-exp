package otus.orm.exp.events;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import otus.orm.exp.entity.Book;
import otus.orm.exp.repository.CommentsRepository;


@Component
public class MongoCommentCascadeDelete extends AbstractMongoEventListener<Book> {

    private final CommentsRepository commentsRepository;

    public MongoCommentCascadeDelete(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        val source = event.getSource();
        String id = source.get("_id").toString();
        commentsRepository.deleteByBookId(id);
    }
}
