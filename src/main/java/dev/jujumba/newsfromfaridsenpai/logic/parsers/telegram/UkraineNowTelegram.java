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
}
