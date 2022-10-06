package dev.jujumba.newsfromfaridsenpai.models;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Jujumba
 */
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String pasword;
    public User() {

    }
}
