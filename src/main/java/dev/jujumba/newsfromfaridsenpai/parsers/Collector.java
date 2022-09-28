package dev.jujumba.newsfromfaridsenpai.parsers;

import dev.jujumba.newsfromfaridsenpai.model.News;
import dev.jujumba.newsfromfaridsenpai.parsers.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.services.MyService;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component
public class Collector {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MyService service;
    @Getter
    private volatile List<News> allNews;
    @Autowired
    public Collector(MyService service) {
        this.service = service;
        allNews = service.findAll();
        Collections.sort(allNews);
    }

    public void collect() {
        Thread newsCleaner = new Thread(new NewsCleaner(this.service));
        newsCleaner.start();

        Thread presidentOfficeThread = new Thread(new PresidentOffice(this));
        Thread pravdaThread = new Thread(new Pravda(this));
        presidentOfficeThread.start();
        pravdaThread.start();
    }

    public void add(News news) {
        allNews.add(news);
        service.save(news);
    }

    public boolean contains(News news) {
        return allNews.contains(news);
    }

    public List<News> getAllNews() {
        Collections.sort(allNews);
        return allNews;
    }
}
