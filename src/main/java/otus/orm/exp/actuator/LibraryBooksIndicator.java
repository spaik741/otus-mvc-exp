package otus.orm.exp.actuator;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import otus.orm.exp.service.BooksService;

@Component
@RequiredArgsConstructor
public class LibraryBooksIndicator implements HealthIndicator {

    private final BooksService booksService;

    @Override
    public Health health() {
        if (CollectionUtils.isNotEmpty(booksService.getAllBooks())) {
            return Health.up()
                    .withDetail("message", "Library books not empty.")
                    .build();
        }
        return Health.down()
                .status(Status.DOWN)
                .withDetail("message", "List books in library is empty!!!")
                .build();
    }
}
