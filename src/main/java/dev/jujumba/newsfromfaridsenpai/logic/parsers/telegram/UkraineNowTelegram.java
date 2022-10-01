package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.Collector;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.springframework.stereotype.Component;

/**
 * @author Jujumba
 */
@Component
public class UkraineNowTelegram extends AbstractTelegramParser{
    public UkraineNowTelegram(Collector collector, TextHandler textHandler, NewsService newsService) {
        super(collector, textHandler, newsService);
        this.setUrl("https://t.me/s/UkraineNow");
    }

    @Override
    boolean ifSuits(Object o) {
        String title = (String) o;
        return title.contains("#") || title.contains("\uD83D\uDD25");
    }
}
