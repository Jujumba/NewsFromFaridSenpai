package dev.jujumba.newsfromfaridsenpai.logic;

import dev.jujumba.newsfromfaridsenpai.logic.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.Pravda;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.PresidentOffice;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram.UkraineNowTelegram;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
/**
 * @author Jujumba
 */
@Component
public class Collector {
    private final NewsService service;
    private final TextHandler textHandler;
    @Getter
    private volatile List<News> allNews;

    @Autowired
    public Collector(NewsService service, TextHandler textHandler) {
        this.service = service;
        allNews = service.findAll();
        this.textHandler = textHandler;
        Collections.sort(allNews);
    }

    @SneakyThrows
    public void collect() {
        Thread newsCleaner = new Thread(new NewsCleaner(this.service));
        newsCleaner.start();
        Thread presidentOfficeThread = new Thread(new PresidentOffice(this,textHandler, service));
        Thread pravdaThread = new Thread(new Pravda(this, textHandler, service));
        Thread tele = new Thread(new UkraineNowTelegram(this, textHandler, service));
        presidentOfficeThread.start();
        pravdaThread.start();
        tele.start();
    }

    public void add(News news) {
        allNews.add(news);
        service.save(news);
    }

    public boolean contains(News news) {
        return service.exists(news);
    }

    public List<News> getAllNews() {
        Collections.sort(allNews);
        return allNews;
    }
}
