package dev.jujumba.newsfromfaridsenpai.logic;

import dev.jujumba.newsfromfaridsenpai.logic.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.Pravda;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.PresidentOffice;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram.UkraineNowTelegram;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
/**
 * @author Jujumba
 */
@Component
public class    Collector {
    private final NewsService service;
    private final TextHandler textHandler;

    @Autowired
    public Collector(NewsService service, TextHandler textHandler) {
        this.service = service;
        this.textHandler = textHandler;
    }

    @SneakyThrows
    public void collect() {
        Thread newsCleaner = new Thread(new NewsCleaner(this.service));
        newsCleaner.setDaemon(true);
        newsCleaner.start();
        Thread presidentOfficeThread = new Thread(new PresidentOffice(textHandler, service));
        Thread pravdaThread = new Thread(new Pravda(textHandler, service));
        Thread tele = new Thread(new UkraineNowTelegram(textHandler, service));
        presidentOfficeThread.start();
        pravdaThread.start();
        tele.start();
    }

    public List<News> getAllNews() {
        List<News> allNews = service.findAll();
        Collections.sort(allNews);
        return allNews;
    }
}
