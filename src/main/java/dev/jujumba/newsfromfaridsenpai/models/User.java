package dev.jujumba.newsfromfaridsenpai.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Jujumba
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    @NotEmpty
    @Size(min = 6, message = "Your email is too short or it has been registered already")
    private String email;
    @Column(name = "password")
    @NotEmpty
    @Size(min = 5, message = "Your password is too short!")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<ApiKey> apiKeys;
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
