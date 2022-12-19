package dev.jujumba.newsfromfaridsenpai.logic;

import dev.jujumba.newsfromfaridsenpai.logic.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.Pravda;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.PresidentOffice;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram.UkraineNowTelegram;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jujumba
 */
@Component
public class Starter {
    private final Pravda pravda;
    private final PresidentOffice presidentOffice;
    private final UkraineNowTelegram ukraineNowTelegram;
    private final NewsCleaner newsCleaner;
    @Autowired
    public Starter(Pravda pravda, PresidentOffice presidentOffice, UkraineNowTelegram ukraineNowTelegram, NewsCleaner newsCleaner) {
        this.pravda = pravda;
        this.presidentOffice = presidentOffice;
        this.ukraineNowTelegram = ukraineNowTelegram;
        this.newsCleaner = newsCleaner;
    }

    @SneakyThrows
    public void collect() {
        ExecutorService service = Executors.newFixedThreadPool(this.getClass().getDeclaredFields().length);
        service.submit(pravda);
        service.submit(presidentOffice);
        service.submit(ukraineNowTelegram);
        service.submit(newsCleaner);
    }
}
