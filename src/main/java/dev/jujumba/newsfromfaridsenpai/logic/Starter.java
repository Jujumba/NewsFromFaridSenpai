package dev.jujumba.newsfromfaridsenpai.logic;

import dev.jujumba.newsfromfaridsenpai.logic.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.PresidentOffice;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram.AbstractTelegramParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.services.ImagesService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jujumba
 */
@Component
public class Starter {
    private final ApplicationContext context;
    private final ExecutorService service;
    private final PresidentOffice presidentOffice;
    private final NewsCleaner newsCleaner;

    @SneakyThrows
    public Starter(PresidentOffice presidentOffice, NewsCleaner newsCleaner, ApplicationContext context) {
        this.presidentOffice = presidentOffice;
        this.newsCleaner = newsCleaner;
        this.context = context;
        service = Executors.newCachedThreadPool();
    }

    @SneakyThrows
    @PostConstruct
    public void load() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(this) instanceof Runnable r)
                service.submit(r);
        }
        runTelegramSources();
    }

    @SneakyThrows
    public void runTelegramSources() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("sources.txt")));
        List<String> sources = reader.lines().toList();
        for (String source : sources) {
            AbstractTelegramParser parser = new AbstractTelegramParser(
                    context.getBean(TextHandler.class),
                    context.getBean(NewsService.class),
                    context.getBean(ImagesService.class)) {
                @Override
                public void parse() {
                    this.setUrl(source);
                    super.parse();
                }
            };
            service.submit(parser);
        }
    }
}
