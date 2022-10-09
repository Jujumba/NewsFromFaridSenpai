package dev.jujumba.newsfromfaridsenpai.logic.parsers.independents;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Jujumba
 */
@Deprecated(forRemoval = true)
public class Pravda extends AbstractParser {
    private final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
    @Autowired
    public Pravda(TextHandler textHandler, NewsService newsService) {
        super(textHandler,newsService);
        setUrl("https://www.pravda.com.ua/news/");
    }

    @Override
    public void run() {
        parse();
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements headers = execQuery(".article_header");
            Elements dates = execQuery(".article_time");
            int counter = 0;
            for (var header : headers) {
                String title = header.text();
                String href = header.getElementsByTag("a").attr("href");
                if (hasOccurred(href)) {
                    logger.warn("Will parse again in 3 minutes");
                    sleep(delay);
                    continue label;
                }
                title = cleanupTitle(title);
                String fullTitle = title;
                title = textHandler.handleTitle(title);
                if (href.charAt(0) == '/') href = "https://www.pravda.com.ua" + href;
                LocalDateTime time = LocalTime.parse(dates.get(counter++).text(), pattern).atDate(LocalDate.now());
                News news = new News(title,href,time, fullTitle);
                if (newsService.save(news,time)) {
                    logger.info("New news found");
                }
            }
            sleep(delay);
        }
    }

    @Override
    public String cleanupTitle(String title) {
        if (title.startsWith("фото")) {
            title = title.replace("фото","");
        } else if (title.startsWith("фото, оновлено")) {
            title = title.replace("фото, оновлено","");
        } else if (title.startsWith("відео")) {
            title = title.replace("відео","");
        }
        return title;
    }
}
