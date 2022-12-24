package dev.jujumba.newsfromfaridsenpai.models;

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
    private int id;
    @Column(name = "img")
    private String img;
    @ManyToOne
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private News news;

    public Image(String img, News news) {
        this.img = img;
        this.news = news;
    }
}
