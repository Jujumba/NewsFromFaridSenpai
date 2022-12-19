package dev.jujumba.newsfromfaridsenpai.models;

import dev.jujumba.newsfromfaridsenpai.models.User;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Jujumba
 */
@Entity
@Table(name = "api_keys")
@Data
//todo: move to models package
public class ApiKey {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public ApiKey() {

    }
}
