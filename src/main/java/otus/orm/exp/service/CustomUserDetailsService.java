package otus.orm.exp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import otus.orm.exp.entity.User;

public interface CustomUserDetailsService extends UserDetailsService {
    User getUserById(Long id);

    User build(User user);
}
