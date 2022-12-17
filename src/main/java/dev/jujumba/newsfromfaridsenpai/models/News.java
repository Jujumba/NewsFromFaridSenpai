package dev.jujumba.newsfromfaridsenpai.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author Jujumba
 */
@Component
@Entity
@Getter
@Setter
@Table(name = "news")
@NoArgsConstructor
public class News {
    private static final DateTimeFormatter FULL_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter HOURS_AND_MINUTES_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "full_title")
    @JsonIgnore
    private String fullTitle;
    @Column(name = "url")
    private String url;
    @Column(name = "date")
    private LocalDateTime dateTime;

    public News(String title, String url, LocalDateTime dateTime, String fullTitle) {
        this.title = title;
        this.url = url;
        this.dateTime = dateTime;
        this.fullTitle = fullTitle;
    }

    public News(String title, String url, String fullTitle) {
        this(title, url, LocalDateTime.now(), fullTitle);
    }

    @JsonIgnore
    public String getFormattedNow() {
        long days = ChronoUnit.DAYS.between(dateTime, LocalDateTime.now());
        if (days == 0) {
            return HOURS_AND_MINUTES_FORMATTER.format(dateTime);
        }
        else {
            return FULL_DATE_FORMATTER.format(dateTime);
        }
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
    public String toString() {
        return getTitle() + " - " + getFormattedNow();
    }
}
