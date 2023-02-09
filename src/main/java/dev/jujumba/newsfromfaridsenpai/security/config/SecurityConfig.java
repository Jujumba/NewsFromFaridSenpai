package dev.jujumba.newsfromfaridsenpai.security.config;

import dev.jujumba.newsfromfaridsenpai.services.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Jujumba
 */
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter /* todo: deprecated */ {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //todo
                .authorizeRequests()
                .antMatchers("/", "/api/v1", "/auth/login", "/styles/*", "/auth/*", "/search/*", "/search") //todo
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/auth/signin")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/profile", true)
                .failureUrl("/auth/signin?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(getEncoder());
    }
}