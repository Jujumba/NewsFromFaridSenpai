package dev.jujumba.newsfromfaridsenpai.models.validation;

import dev.jujumba.newsfromfaridsenpai.models.User;
import dev.jujumba.newsfromfaridsenpai.services.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Jujumba
 */
@Component
@AllArgsConstructor
public class UserValidator implements Validator {
    private final MyUserDetailsService userDetailsService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            userDetailsService.loadUserByUsername(user.getEmail());
        } catch (UsernameNotFoundException ignored) {

        }

        errors.rejectValue("email","","This email is already in use!");
    }
}
