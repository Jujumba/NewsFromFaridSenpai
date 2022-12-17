package dev.jujumba.newsfromfaridsenpai.logic;

import dev.jujumba.newsfromfaridsenpai.logic.cleaner.NewsCleaner;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.Pravda;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.independents.PresidentOffice;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram.UkraineNowTelegram;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        Thread newsCleanerThread = new Thread(newsCleaner);
        newsCleanerThread.setDaemon(true);

        Thread presidentOfficeThread = new Thread(presidentOffice);
        Thread pravdaThread = new Thread(pravda);
        Thread tele = new Thread(ukraineNowTelegram);

//        presidentOfficeThread.start();
//        pravdaThread.start();
        tele.start();
//        newsCleanerThread.start();
    }
}
