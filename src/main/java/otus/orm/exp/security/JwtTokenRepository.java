package otus.orm.exp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;

@Repository
public class JwtTokenRepository implements CsrfTokenRepository {

    @Getter
    private final String secret = "otus_book";


    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String id = UUID.randomUUID().toString().replace("-", "");

        String token = "";
        try {
            token = Jwts.builder()
                    .setId(id)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (JwtException e) {
            e.printStackTrace();
        }
        return new DefaultCsrfToken("token", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token != null) {
            if (!response.getHeaderNames().contains(ACCESS_CONTROL_EXPOSE_HEADERS)) {
                response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, token.getHeaderName());
            }

            if (response.getHeaderNames().contains(token.getHeaderName())) {
                response.setHeader(token.getHeaderName(), token.getToken());
            } else {
                response.addHeader(token.getHeaderName(), token.getToken());
            }

        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
        return null;
    }
}
