package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dev.jujumba.newsfromfaridsenpai.models.User;

/**
 * @author Jujumba
 */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}
