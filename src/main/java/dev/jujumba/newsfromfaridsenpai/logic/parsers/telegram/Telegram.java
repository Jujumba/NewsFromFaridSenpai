package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.Collector;
import dev.jujumba.newsfromfaridsenpai.logic.parsers.Parser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
/**
 * @author Jujumba
 */
public class Telegram implements Runnable, Parser {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Collector collector;
    private final TextHandler textHandler;
    private final NewsService newsService;
    private String url = "https://t.me/s/UkraineNow";
    @Autowired
    public Telegram(Collector collector, TextHandler textHandler, NewsService newsService) {
        this.collector = collector;
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
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
                logger.info("Connected to " + url);
            } catch (IOException e) {
                logger.error("Unable to connect to " + url);
                e.printStackTrace();
            }
            Elements elements = doc.select(".tgme_widget_message_text");
            Elements hrefs = doc.select(".tgme_widget_message_date");
            for (int i = 0; i < elements.size(); i++) {
                if (i >= hrefs.size()) break;
                String title = elements.get(i).text();
                String fullTitle = title;
                String href = hrefs.get(i).attr("href");
                if (newsService.existsByFullTitle(title) || newsService.existsByUrl(href)) {
                    LocalTime now = LocalTime.now();
                    now = now.plusMinutes(3);
                    logger.warn("Continuing to while(true) loop. Will parse again in "+now);
                    sleep(240);
                    continue label;
                }
                /**
                 * Only for UkraineNOW
                 */
                if (title.contains("#") || title.contains("\uD83D\uDD25")) {
                    logger.warn("Unsuitable news has been found",title);
                    continue;
                } else {
                    title = textHandler.handleTitle(title);
                }

                LocalDateTime now = LocalDateTime.parse(hrefs.get(i).getElementsByTag("time").get(0).attr("datetime").split("\\+")[0]);
                News news = new News(title,href,now, fullTitle);
                if (!collector.contains(news)) {
                    collector.add(news);
                    logger.info("New news found");
                }
            }
            sleep(180);
        }
    }
}
