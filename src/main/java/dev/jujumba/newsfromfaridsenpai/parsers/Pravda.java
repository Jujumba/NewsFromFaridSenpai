package dev.jujumba.newsfromfaridsenpai.parsers;

import dev.jujumba.newsfromfaridsenpai.model.News;
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

    @Autowired
    public Pravda(Collector collector) {
        this.collector = collector;
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
                if (title.startsWith("фото")) {
                    title = title.replace("фото","");
                } else if (title.startsWith("фото, оновлено")) {
                    title = title.replace("фото, оновлено","");
                } else if (title.startsWith("відео")) {
                    title = title.replace("відео","");
                }
                String href = header.getElementsByTag("a").attr("href");
                if (href.charAt(0) == '/') href = "https://www.pravda.com.ua" + href;
                LocalDateTime time = LocalTime.parse(dates.get(counter++).text(), pattern).atDate(LocalDate.now());
                News news = new News(title,href,time);
                if (!collector.contains(news)) {
                    collector.add(news);
                }else {
                    logger.warn("Continuing to while(true) loop");
                    sleep(240);
                    continue label;
                }
            }
                sleep(240);
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
