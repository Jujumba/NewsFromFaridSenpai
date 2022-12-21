package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Jujumba
 */
public abstract class AbstractTelegramParser extends AbstractParser {
    @Autowired
    public AbstractTelegramParser(TextHandler textHandler, NewsService newsService) {
        super(textHandler, newsService);
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements elements = execQuery(".tgme_widget_message_text");
            Elements messageData = execQuery(".tgme_widget_message_date");

            int hrefsPointer = messageData.size() - 1;
            String prevTitle = null;

            for (int i = elements.size() - 1; i >= 0; i--) {
                if (elements.get(i).text().equals(prevTitle) || elements.get(i).className().contains("reply")) {
                    continue;
                }
                Element dataElement = messageData.get(hrefsPointer);

                String href = dataElement.attr("href");
                String title = textHandler.handleTitle(elements.get(i).text());

                LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.parse(dataElement.text()));

                if (hasOccurred(href)) {
                    logger.warn("Duplicate news has been found. Continuing to parse after 3 minutes delay");
                    sleep(getDelay());
                    continue label;
                }


                News news = new News(title, href,date);
                newsService.save(news);

                prevTitle = elements.get(i).text();
                hrefsPointer -= 1;
            }
            sleep(delay);
        }
    }
}
