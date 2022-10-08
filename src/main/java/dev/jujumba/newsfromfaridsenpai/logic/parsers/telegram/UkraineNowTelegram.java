package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.springframework.stereotype.Component;

/**
 * @author Jujumba
 */
@Component
public class UkraineNowTelegram extends AbstractTelegramParser{
    public UkraineNowTelegram(TextHandler textHandler, NewsService newsService) {
        super(textHandler, newsService);
        setUrl("https://t.me/s/UkraineNow");
    }

    @Override
    public boolean notSuits(Object o) {
        String title = (String) o;
        return title.contains("#") || title.contains("\uD83D\uDD25");
    }

    @Override
    public String cleanupTitle(String title) {
        return title;
    }
}
