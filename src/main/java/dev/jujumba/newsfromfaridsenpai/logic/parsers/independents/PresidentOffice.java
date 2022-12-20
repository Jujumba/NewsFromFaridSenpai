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
@Component
public class PresidentOffice extends AbstractParser {
    @Autowired
    public PresidentOffice(TextHandler textHandler, NewsService newsService) {
        super(textHandler,newsService);
        setUrl("https://www.president.gov.ua/news/last");
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements selected = execQuery(".item_stat_headline");
            for (var elem : selected) {
                String title = elem.getElementsByTag("h3").text();

                String href = elem.getElementsByTag("a").attr("href");
                if (hasOccurred(href)) {
                    logger.warn("Waiting 3 minutes, then`ll parse again");
                    sleep(getDelay());
                    continue label;
                }

                String fullTitle = title;
                title = textHandler.handleTitle(title);

                News news = new News(title, href, fullTitle);
                newsService.save(news);
            }
            sleep(getDelay());
        }
    }
}
