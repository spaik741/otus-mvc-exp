package otus.orm.exp.actuator;

import lombok.RequiredArgsConstructor;
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
        long count = booksService.getCountBooks();
        if (count > 0) {
            return Health.up()
                    .withDetail("message", String.format("Count books in library: %s", count))
                    .build();
        }
        return Health.down()
                .status(Status.DOWN)
                .withDetail("message", "List books in library is empty!!!")
                .build();
    }
}
