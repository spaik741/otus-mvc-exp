package otus.orm.exp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import otus.orm.exp.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAll();
    User getUserByLogin(String login);
}
