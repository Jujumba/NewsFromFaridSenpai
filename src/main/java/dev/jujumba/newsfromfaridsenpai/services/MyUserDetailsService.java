package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.User;
import dev.jujumba.newsfromfaridsenpai.repositories.UserRepository;
import dev.jujumba.newsfromfaridsenpai.security.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Jujumba
 */
@Service
public class MyUserDetailsService extends AbstractService<User> implements UserDetailsService {
    public MyUserDetailsService(UserRepository repository) {
        super(repository);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = ((UserRepository) repository).findUserByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("No such user!");
        }

        return new MyUserDetails(userOptional.get());
    }
}
