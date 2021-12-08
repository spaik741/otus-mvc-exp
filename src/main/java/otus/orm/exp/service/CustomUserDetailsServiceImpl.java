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
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                mapRolesToAuthorities(user.getRoles()));
    }

    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public User build(User user) {
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//        return new User(user.getId(),
//                user.getUsername(),
//                user.getPassword(),
//                user.getName(),
//                authorities);

        return new User(user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getRoles());
    }

//    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }
}
