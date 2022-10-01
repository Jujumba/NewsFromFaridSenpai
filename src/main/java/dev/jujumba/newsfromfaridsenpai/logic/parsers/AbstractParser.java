package dev.jujumba.newsfromfaridsenpai.logic.parsers;

import lombok.Data;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Jujumba
 */
@Data
public abstract class AbstractParser implements Parser, Runnable {
    private String url;
    private Document document;
    private int delay = 180;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @SneakyThrows
    public Document connect() {
        Document document = Jsoup.connect(url).get();
        logger.info("Connected to "+url);
        return document;
    }

    public Elements execQuery(Document doc, String query) {
        return doc.select(query);
    }

    public abstract String cleanupTitle(String title);

    public boolean hasOccurred(Object... args) {
        return false;
    }
    public abstract boolean hasOccurred(String fullTitle, String href);
}
