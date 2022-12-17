package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jujumba
 */
public abstract class AbstractTelegramParser extends AbstractParser {
    @Autowired
    public AbstractTelegramParser(TextHandler textHandler, NewsService newsService) {
        super(textHandler, newsService);
    }

    @Override
    public void run() {
        parse();
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements elements = execQuery(".tgme_widget_message_text");
            Elements hrefs = execQuery(".tgme_widget_message_date");

            int hrefsPointer = 0;
            String prevTitle = null;

            for (var element : elements) {
                if (element.text().equals(prevTitle) || element.className().contains("reply")) {
                    continue;
                }


                String href = hrefs.get(hrefsPointer++).attr("href");
                String fullTitle = element.text(); //todo: remove
                String handledTitle = element.text();

                if (hasOccurred(href)) {
                    logger.warn("Unsuitable news has been found");
                    continue label;
                }

                handledTitle = textHandler.handleTitle(handledTitle);

                News news = new News(handledTitle, href, fullTitle);
                newsService.save(news);

                prevTitle = element.text();
            }
            sleep(delay);
        }
    }
}
