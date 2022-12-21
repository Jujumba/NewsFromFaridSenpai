package dev.jujumba.newsfromfaridsenpai.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Random;


/**
 * @author Jujumba
 */
@Entity
@Table(name = "api_keys")
@Data
public class ApiKey {
    private static final int leftLimit = 48;
    private static final int rightLimit = 122;
    private static final int keyLength = 50;

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
        initValue(); //todo: rename
    }

    private void initValue() {
        Random random = new Random((long) (3 + Math.random() * 999999999));

        value = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(25)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString(); //todo: optimize solution from net
    }
}
