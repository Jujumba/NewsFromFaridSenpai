package dev.jujumba.newsfromfaridsenpai.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Jujumba
 */
@Table(name = "images")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(name = "url")
    private String url;
    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    @JsonIgnore
    private News news;

    public Image(String url, News news) {
        this.url = url;
        this.news = news;
    }
}
