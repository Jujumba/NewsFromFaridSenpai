package dev.jujumba.newsfromfaridsenpai.security;

import dev.jujumba.newsfromfaridsenpai.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Jujumba
 */
@Component
@AllArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        UserDetails userDetails = userService.loadUserByUsername(email);
        String password = authentication.getCredentials().toString();

        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList()); //todo
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
