package dev.jujumba.newsfromfaridsenpai.api.apimodels;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Jujumba
 */
@Entity
@Table(name = "api_owners")
@Data
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
