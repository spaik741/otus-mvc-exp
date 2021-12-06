package otus.orm.exp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.core.Authentication;

public interface JWTTokenProvider {
    String generateToken(Authentication authentication);

    Jws<Claims> parserToken(String token);

    Long getUserIdFromToken(Jws<Claims> token);

}
