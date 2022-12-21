package dev.jujumba.newsfromfaridsenpai.logic;

import dev.jujumba.newsfromfaridsenpai.logic.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.PresidentOffice;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram.UkraineNowTelegram;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

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
        collect();
    }

    @SneakyThrows
    public void collect() {
        ExecutorService service = Executors.newFixedThreadPool(this.getClass().getDeclaredFields().length);
        service.submit(presidentOffice);
        service.submit(ukraineNowTelegram);
        service.submit(newsCleaner);
    }
}
