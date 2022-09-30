package dev.jujumba.newsfromfaridsenpai.logic.parsers.telegram;

import dev.jujumba.newsfromfaridsenpai.logic.Collector;
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
import java.util.concurrent.TimeUnit;

@Component
public class TeleTest implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Collector collector;
    private final TextHandler textHandler;
    private final NewsService newsService;
    @Autowired
    public TeleTest(Collector collector, TextHandler textHandler, NewsService newsService) {
        this.collector = collector;
        this.textHandler = textHandler;
        this.newsService = newsService;
    }

    @Override
    public void run() {
        label: while (true) {
            Document doc;
            try {
                doc = Jsoup.connect("https://t.me/s/UkraineNow").get();
                logger.info("Connected to https://t.me/s/UkraineNow");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements elements = doc.select(".tgme_widget_message_text");
            Elements hrefs = doc.select(".tgme_widget_message_date");
            for (int i = 0; i < elements.size(); i++) {
                if (i >= hrefs.size()) break;
                String title = elements.get(i).text();
                String fullTitle = title;
                String href = hrefs.get(i).attr("href");
                if (newsService.existsByFullTitle(title) || newsService.existsByUrl(href)) {
                    logger.warn("Continuing to while(true) loop");
                    sleep(240);
                    continue label;
                }

                if (title.contains("#") || title.contains("\uD83D\uDD25")) {
                    logger.info("Unsuitable news has been found",title);
                    continue;
                } else {
                    title = textHandler.handleTitle(title);
                }

                LocalDateTime now = LocalDateTime.parse(hrefs.get(i).getElementsByTag("time").get(0).attr("datetime").split("\\+")[0]);
                News news = new News(title,href,now, fullTitle);
                if (!collector.contains(news)) {
                    collector.add(news);
                    logger.info("New news has been found");
                }
            }
            sleep(180);
        }
    }
    private synchronized void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
