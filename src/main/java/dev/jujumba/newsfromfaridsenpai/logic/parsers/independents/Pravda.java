package dev.jujumba.newsfromfaridsenpai.logic.parsers.independents;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jujumba
 */
@Deprecated(forRemoval = true)
@Component
public class Pravda extends AbstractParser {
    @Autowired
    public Pravda(TextHandler textHandler, NewsService newsService) {
        super(textHandler,newsService);
        setUrl("https://www.pravda.com.ua/news/");
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements headers = execQuery(".article_header");
            for (var header : headers) {
                String title = textHandler.handleTitle(header.text());
                String href = header.getElementsByTag("a").attr("href");

                if (hasOccurred(href)) {
                    logger.warn("Waiting 3 minutes, then`ll parse again");
                    sleep(delay);
                    continue label;
                }

                if (href.charAt(0) == '/') href = "https://www.pravda.com.ua" + href;

                News news = new News(title,href);
                newsService.save(news);
            }
            sleep(delay);
        }
    }
}
