package dev.jujumba.newsfromfaridsenpai.api.apimodels;

import javax.persistence.*;

/**
 * @author Jujumba
 */
@Entity
@Table(name = "api_owners")
public class ApiKeyOwner {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;

    public ApiKeyOwner() {

    }
}
