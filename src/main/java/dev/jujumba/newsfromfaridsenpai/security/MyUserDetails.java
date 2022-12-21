package dev.jujumba.newsfromfaridsenpai.security;

import dev.jujumba.newsfromfaridsenpai.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Jujumba
 */
@AllArgsConstructor
public final class MyUserDetails implements UserDetails {
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //todo
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //todo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //todo
    }

    @Override
    public boolean isEnabled() {
        return true; //todo
    }
}
