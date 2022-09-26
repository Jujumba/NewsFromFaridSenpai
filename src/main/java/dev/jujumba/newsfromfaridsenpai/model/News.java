package dev.jujumba.newsfromfaridsenpai.model;

import org.hibernate.engine.loading.internal.LoadContexts;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
@Entity
public class News {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
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
    public News() {

    }

    public News(String title, String url) {
        this.title = title;
        this.url = url;
        now = LocalDateTime.parse(formatter.format(LocalDateTime.now()), formatter);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
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
}
