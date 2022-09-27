package dev.jujumba.newsfromfaridsenpai.parsers;

import dev.jujumba.newsfromfaridsenpai.model.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
    @Autowired
    public Pravda(Collector collector) {
        this.collector = collector;
    }

    @Override
    public void run() {
        while (true) {
            Document doc;
            try {
                doc = Jsoup.connect("https://www.pravda.com.ua/rus/news/").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements headers = doc.select(".article_header");
            Elements dates = doc.select(".article_time");
            int counter = 0;
            for (var header : headers) {
                String title = header.text();
                String href = header.getElementsByTag("a").attr("href");
                if (href.charAt(0) == '/') href = "https://www.pravda.com.ua" + href;
                LocalDateTime time = LocalTime.parse(dates.get(counter++).text(), pattern).atDate(LocalDate.now());
                News news = new News(title,href,time);
                if (!collector.contains(news)) {
                    collector.add(news);
                }
            }
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
