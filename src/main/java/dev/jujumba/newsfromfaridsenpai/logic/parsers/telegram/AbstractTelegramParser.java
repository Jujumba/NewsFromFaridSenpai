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

            int hrefsPointer = hrefs.size() - 1;
            String prevTitle = null;

            for (int i = elements.size() - 1; i >= 0; i--) {
                if (elements.get(i).text().equals(prevTitle) || elements.get(i).className().contains("reply")) {
                    continue;
                }


                String href = hrefs.get(hrefsPointer--).attr("href");
                String fullTitle = elements.get(i).text(); //todo: remove
                String handledTitle = elements.get(i).text();

                if (hasOccurred(href)) {
                    logger.warn("Unsuitable news has been found");
                    sleep(getDelay());
                    continue label;
                }

                handledTitle = textHandler.handleTitle(handledTitle);

                News news = new News(handledTitle, href, fullTitle);
                newsService.save(news);

                prevTitle = elements.get(i).text();
            }
            sleep(delay);
        }
    }
}
