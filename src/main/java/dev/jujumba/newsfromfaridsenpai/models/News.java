package dev.jujumba.newsfromfaridsenpai.models;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Jujumba
 */
@Component
@Entity
@Data
public class News implements Comparable<News> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "full_title")
    private String fullTitle;
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
    public News(String title, String url, LocalDateTime now, String fullTitle) {
        if (title.chars().filter(ch -> ch == '\"').count() == 1) title = title.replace("\"","");
        this.title = title;
        this.url = url;
        this.now = now;
        this.fullTitle = fullTitle;
    }

    public News(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public String getFormattedNow() {
        if (now.getYear() == LocalDateTime.now().getYear() && now.getMonth() == LocalDateTime.now().getMonth() && now.getDayOfMonth() == LocalDateTime.now().getDayOfMonth())
        return formatter1.format(now);
        else return formatter.format(now);
    }

    public String getTitle() {
        return title;
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
