package dev.jujumba.newsfromfaridsenpai.models;

import lombok.*;

import javax.persistence.*;

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
    private String email;
    @Column(name = "password")
    private String password;
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
