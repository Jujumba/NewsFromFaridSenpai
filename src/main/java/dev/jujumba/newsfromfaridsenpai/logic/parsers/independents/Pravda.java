package dev.jujumba.newsfromfaridsenpai.logic.parsers.independents;

import dev.jujumba.newsfromfaridsenpai.logic.Collector;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class Pravda implements Runnable {
    private volatile Collector collector;
    private final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("HH:mm");
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final TextHandler textHandler;
    private final NewsService newsService;
    @Autowired
    public Pravda(Collector collector, TextHandler textHandler, NewsService newsService) {
        this.collector = collector;
        this.textHandler = textHandler;
        this.newsService = newsService;
    }

    @Override
    public void run() {
        label: while (true) {
            Document doc;
            try {
                doc = Jsoup.connect("https://www.pravda.com.ua/news/").get();
                logger.info("Conneted to https://www.pravda.com.ua/news/");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements headers = doc.select(".article_header");
            Elements dates = doc.select(".article_time");
            int counter = 0;
            for (var header : headers) {
                String title = header.text();
                if (newsService.existsByFullTitle(title)) {
                    logger.warn("Continuing to while(true) loop");
                    sleep(240);
                    continue label;
                }
                if (title.startsWith("фото")) {
                    title = title.replace("фото","");
                } else if (title.startsWith("фото, оновлено")) {
                    title = title.replace("фото, оновлено","");
                } else if (title.startsWith("відео")) {
                    title = title.replace("відео","");
                }
                String fullTitle = title;
                title = textHandler.handleTitle(title);
                String href = header.getElementsByTag("a").attr("href");
                if (href.charAt(0) == '/') href = "https://www.pravda.com.ua" + href;
                LocalDateTime time = LocalTime.parse(dates.get(counter++).text(), pattern).atDate(LocalDate.now());
                News news = new News(title,href,time, fullTitle);
                if (!collector.contains(news) && (LocalDateTime.now().getDayOfMonth() - time.getDayOfMonth() <= 2)) {
                    collector.add(news);
                }
            }
                sleep(60);
        }
    }

    private synchronized void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
