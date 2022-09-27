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
                doc = Jsoup.connect("https://www.president.gov.ua/ua").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements withAAttr = doc.select("a");
            Elements dates = doc.select(".date");
            int counter = 0;
            for (var elem : withAAttr) {
                String href = elem.attr("href");
                if (href.contains("https://www.president.gov.ua/news/") && href.contains("-")) {
                    String date = dates.get(counter++).text(); //26 вересня 2022 року - 22:16
                    String[] split = date.split(" ");
                    StringBuilder resultDate = new StringBuilder();
                    String month = split[1];
                    switch (month.trim()) {
                        case "січня" -> month = "01";
                        case "лютого" -> month = "02";
                        case "березня" -> month = "03";
                        case "квітня" -> month = "04";
                        case "травня" -> month = "05";
                        case "червня" -> month = "06";
                        case "липня" -> month = "07";
                        case "серпня" -> month = "08";
                        case "вересня" -> month = "09";
                        case "жовтня" -> month = "10";
                        case "листопада" -> month = "11";
                        case "грудня" -> month = "12";
                    }
                    resultDate.append(split[2]).append(" ");
                    resultDate.append(month).append(" ");
                    resultDate.append(split[5]);
                    LocalDateTime dateTime = LocalDateTime.parse(resultDate.toString(), formatter);
                    News news = new News(elem.text(), href, dateTime); //todo
                    if (!collector.contains(news)) {
                        collector.add(news);
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
