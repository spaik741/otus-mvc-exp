package otus.orm.exp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import otus.orm.exp.entity.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
}
