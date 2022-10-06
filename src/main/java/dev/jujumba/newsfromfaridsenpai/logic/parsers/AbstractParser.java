package dev.jujumba.newsfromfaridsenpai.logic.parsers;

import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.Data;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Jujumba
 */
@Data
@Component
public abstract class AbstractParser implements Parser, Runnable {
    private String url;
    public Document document;
    public TextHandler textHandler;
    public NewsService newsService;

    public int delay = 180;
    public final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public AbstractParser(TextHandler textHandler, NewsService newsService) {
        this.textHandler = textHandler;
        this.newsService = newsService;
    }

    public AbstractParser() {
    }

    @SneakyThrows
    public void connect() {
        document = Jsoup.connect(url).get();
        logger.info("Connected to "+url);
    }

    public Elements execQuery(String query) {
        return document.select(query);
    }

    public abstract String cleanupTitle(String title);

    public boolean hasOccurred(Object... args) {
        return false;
    }
    public abstract boolean hasOccurred(String fullTitle, String href);
}
