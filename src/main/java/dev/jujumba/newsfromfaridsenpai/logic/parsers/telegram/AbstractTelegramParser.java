package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.Image;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.ImagesService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jujumba
 */
@Component
public abstract class AbstractTelegramParser extends AbstractParser {

    private final ImagesService imagesService;

    @Autowired
    public AbstractTelegramParser(TextHandler textHandler, NewsService newsService, ImagesService imagesService) {
        super(textHandler, newsService);
        this.imagesService = imagesService;
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements messages = execQuery(".tgme_widget_message_wrap");
            //todo: REFACTOR UNREADABLE CODE
            for (int i = messages.size() - 1; i >= 0; i--) {
                Element current = messages.get(i);
                Element date = current.select(".tgme_widget_message_date").get(0);
                String href = date.attr("href");

                if (hasOccurred(href)) {
                    sleep(delay);
                    continue label;
                }

                String title = current.select(".tgme_widget_message_text").text();
                LocalDateTime dateTime = LocalDateTime.parse(date.firstElementChild().attr("datetime"), DateTimeFormatter.ISO_ZONED_DATE_TIME);

                News news = new News(title, href, dateTime);

                Elements imagesElements = current.select(".tgme_widget_message_photo_wrap");
                List<Image> images = new ArrayList<>();
                for (Element imageElement : imagesElements) {
                    String imgUrl = imageElement.attr("style");
                    imgUrl = imgUrl.substring(imgUrl.indexOf('\'') + 1, imgUrl.indexOf(')') - 1); //todo <(^.^)> ?
                    images.add(new Image(imgUrl, news));
                }
                newsService.save(news);
                imagesService.saveAll(images);
            }

            sleep(delay);
        }
    }
}
