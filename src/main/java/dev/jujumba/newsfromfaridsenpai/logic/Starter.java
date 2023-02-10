package dev.jujumba.newsfromfaridsenpai.logic;

import dev.jujumba.newsfromfaridsenpai.logic.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.PresidentOffice;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram.UkraineNowTelegram;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jujumba
 */
@Component
public class Starter {
    private final PresidentOffice presidentOffice;
    private final UkraineNowTelegram ukraineNowTelegram;
    private final NewsCleaner newsCleaner;

    public Starter(PresidentOffice presidentOffice, UkraineNowTelegram ukraineNowTelegram, NewsCleaner newsCleaner) {
        this.presidentOffice = presidentOffice;
        this.ukraineNowTelegram = ukraineNowTelegram;
        this.newsCleaner = newsCleaner;
    }

    @SneakyThrows
    @PostConstruct
    public void load() {
        Field[] fields = this.getClass().getDeclaredFields();
        ExecutorService service = Executors.newFixedThreadPool(fields.length);
        for (Field field : fields) {
            field.setAccessible(true);
            Runnable r = (Runnable) field.get(this);
            service.submit(r);
        }
    }
}
