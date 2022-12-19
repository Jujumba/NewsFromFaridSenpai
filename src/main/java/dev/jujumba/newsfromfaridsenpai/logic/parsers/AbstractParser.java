package dev.jujumba.newsfromfaridsenpai.logic.parsers;

import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Component
@Data
@NoArgsConstructor
public abstract class AbstractParser implements Parser, Runnable {
    protected String url;
    protected Document document;
    protected TextHandler textHandler;
    protected NewsService newsService;

    protected int delay = 180;
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected AbstractParser(TextHandler textHandler, NewsService newsService) {
        this.textHandler = textHandler;
        this.newsService = newsService;
    }

    @SneakyThrows
    protected void connect() {
        document = Jsoup.connect(url).get();
        logger.info("Connected to " + url);
    }

    protected Elements execQuery(String query) {
        return document.select(query);
    }

    protected abstract String cleanupTitle(String title);

    @Override
    public void run() {
        parse();
    }

    protected boolean hasOccurred(String href) {
        return newsService.existsByUrl(href);
    }
}
