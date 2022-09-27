package dev.jujumba.newsfromfaridsenpai.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@Entity
@Data
public class News implements Comparable<News> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "url")
    private String url;
    @Column(name = "date")
    private LocalDateTime now;
    public News () {
        now = LocalDateTime.parse(formatter.format(LocalDateTime.now()), formatter);
    }

    public News(String title, String url) {
        this.title = title;
        this.url = url;
        now = LocalDateTime.parse(formatter.format(LocalDateTime.now()), formatter);
    }

    public News(String title, String url, LocalDateTime now) {
        this.title = title;
        this.url = url;
        this.now = now;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(title, news.title) && Objects.equals(url, news.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }

    @Override
    public int compareTo(News o) {
        if (o.getNow() == null) return 1;
        else if (this.getNow() == null) return -1;


        if (this.getNow().isBefore(o.getNow())) {
            return 1;
        }
        else if (this.getNow().isAfter(o.getNow())) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
