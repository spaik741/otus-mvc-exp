package otus.orm.exp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import otus.orm.exp.handler.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfigurer {

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BookHandler bookHandler,
                                                         CommentHandler commentHandler,
                                                         AuthorHandler authorHandler,
                                                         GenreHandler genreHandler) {
        return route()
                .path("/books", b -> b
                        .GET("", accept(APPLICATION_JSON), bookHandler::all)
                        .GET("/{id}", bookHandler::byId)
                        .DELETE("/{id}", bookHandler::delete)
                        .POST("", bookHandler::save))
                .path("/comments", c -> c
                        .GET("/{idBook}", accept(APPLICATION_JSON), commentHandler::byBookId))
                .path("/authors", a -> a
                        .GET("", accept(APPLICATION_JSON), authorHandler::all))
                .path("/genres", g -> g
                        .GET("", accept(APPLICATION_JSON), genreHandler::all))
                .build();

    }

}
