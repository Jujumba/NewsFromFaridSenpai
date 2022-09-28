package dev.jujumba.newsfromfaridsenpai.parsers;

import dev.jujumba.newsfromfaridsenpai.model.News;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
@Getter
public class PresidentOffice implements Runnable {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");

    private volatile Collector collector;
    private final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
    @Autowired
    public PresidentOffice(Collector collector) {
        this.collector = collector;
    }
    @Override
    public void run() {
        while (true) {
            Document doc;
            try {
                doc = Jsoup.connect("https://www.president.gov.ua/news/last").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements withAAttr = doc.select(".item_stat_headline");
            for (var elem : withAAttr) {
                String href = elem.getElementsByTag("a").attr("href");
                String title = elem.text();
                String[] split = title.split(" ");

                int day = Integer.parseInt(split[split.length - 6]);
                int year = Integer.parseInt(split[split.length - 4]);
                Month month = switch (split[split.length - 5]) {
                    case "січня" -> Month.JANUARY;
                    case "лютого" -> Month.FEBRUARY;
                    case "березня" -> Month.MARCH;
                    case "квітня" -> Month.APRIL;
                    case "травня" -> Month.MAY;
                    case "червня" -> Month.JUNE;
                    case "липня" -> Month.JULY;
                    case "серпня" -> Month.AUGUST;
                    case "вересня" -> Month.SEPTEMBER;
                    case "жовтня" -> Month.OCTOBER;
                    case "листопада" -> Month.NOVEMBER;
                    case "грудня" -> Month.DECEMBER;
                    default -> throw new RuntimeException("PARSE ERROR");
                };
                String[] hourAndMinute = split[split.length - 1].split(":");
                int hour = Integer.parseInt(hourAndMinute[0]);
                int minute = Integer.parseInt(hourAndMinute[1]);

              LocalDateTime dateTime = LocalDateTime.of(year,month ,day, hour, minute);
              News news = new News(title, href, dateTime);
              if (!collector.contains(news)) collector.add(news);
            }
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
