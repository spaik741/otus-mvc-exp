package otus.orm.exp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otus.orm.exp.entity.User;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
    List<User> findAll();
}
