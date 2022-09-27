package dev.jujumba.newsfromfaridsenpai.parsers;

import dev.jujumba.newsfromfaridsenpai.model.News;
import dev.jujumba.newsfromfaridsenpai.services.MyService;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Getter
public class PresidentOffice {
    private MyService service;
    private volatile List<News> result = new ArrayList<>();
    @Autowired
    public PresidentOffice(MyService service) {
        this.service = service;
    }
    public void parse() {
        while (true) {
            Document doc;
            try {
                doc = Jsoup.connect("https://www.president.gov.ua/ua").get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements withAAttr = doc.select("a");
            for (var elem : withAAttr) {
                String href = elem.attr("href");
                if (href.contains("https://www.president.gov.ua/news/") && href.contains("-")) {
                    News news = new News(elem.text(), href);
                    if (!service.exists(news)) {
                        service.save(news);
                        result.add(news);
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
