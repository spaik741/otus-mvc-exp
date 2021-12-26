package otus.orm.exp.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import otus.orm.exp.entity.User;
import otus.orm.exp.repository.UsersRepository;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username not found with username %s", username)));
        return build(user);
    }

    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public User build(User user) {
        return new User(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getRoles());
    }

}
