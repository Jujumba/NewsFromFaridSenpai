package dev.jujumba.newsfromfaridsenpai.api.apimodels;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Jujumba
 */
@Entity
@Table(name = "api_keys")
@Data
public class ApiKey {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "value")
    private String value;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private ApiKeyOwner owner;

    public ApiKey() {

    }
}
