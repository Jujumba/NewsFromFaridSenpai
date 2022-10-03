package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.Data;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Jujumba
 */
@Data
public abstract class AbstractTelegramParser extends AbstractParser {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final TextHandler textHandler;
    private final NewsService newsService;
    @Autowired
    public AbstractTelegramParser(TextHandler textHandler, NewsService newsService) {
        this.textHandler = textHandler;
        this.newsService = newsService;
    }

    @Override
    public void run() {
        parse();
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements elements = execQuery(getDocument(),".tgme_widget_message_text");
            Elements hrefs = execQuery(getDocument(),".tgme_widget_message_date");
            int counter = 0;
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).classNames().contains("js-message_reply_text")) continue;
                String title = elements.get(i).text();
                String fullTitle = title;
                String href = hrefs.get(counter++).attr("href");
                if (hasOccurred(title,href)) {
                    LocalTime now = LocalTime.now();
                    now = now.plusMinutes(3);
                    logger.warn("Continuing to while(true) loop. Will parse again in "+now);
                    sleep(240);
                    continue label;
                }

                if (notSuits(title)) {
                    logger.warn("An unsuitable news has been found",title);
                    continue;
                }
                title = cleanupTitle(title);
                title = textHandler.handleTitle(title);

                LocalDateTime now = LocalDateTime.parse(hrefs.get(i).getElementsByTag("time").get(0).attr("datetime").split("\\+")[0]);
                News news = new News(title,href,now, fullTitle);
                if (!newsService.exists(news)) {
                    newsService.save(news);
                    logger.info("New news found");
                }
            }
            sleep(180);
        }
    }
    abstract boolean notSuits(Object o);
}
