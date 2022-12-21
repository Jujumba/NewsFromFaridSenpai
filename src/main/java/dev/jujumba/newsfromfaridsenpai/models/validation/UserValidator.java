package dev.jujumba.newsfromfaridsenpai.models.validation;

import dev.jujumba.newsfromfaridsenpai.models.User;
import dev.jujumba.newsfromfaridsenpai.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Jujumba
 */
@Component
@AllArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (userService.findByEmail(user.getEmail()) == null) {
            return;
        }

        errors.rejectValue("email","","This email is already in use!");
    }
}
