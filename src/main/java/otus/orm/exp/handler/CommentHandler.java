package otus.orm.exp.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface CommentHandler {

    Mono<ServerResponse> byBookId(ServerRequest request);

}
