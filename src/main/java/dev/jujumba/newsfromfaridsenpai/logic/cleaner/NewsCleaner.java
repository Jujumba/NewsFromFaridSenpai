package dev.jujumba.newsfromfaridsenpai.logic.cleaner;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Jujumba
 */
@Component
public class NewsCleaner extends AbstractParser {
    @Autowired
    public NewsCleaner(NewsService service) {
        super(null,service);
    }

    @Override
    public void run() {
        parse();
    }

    @Override
    public void parse() {
        while (true) {
            logger.warn("Starting news cleaning");
            int currentDay = LocalDateTime.now().getDayOfMonth();
            for (var news : newsService.findAll()) {
                int newsDay = news.getDateTime().getDayOfMonth();
                if (currentDay - newsDay >= 3 || currentDay - newsDay < 0)
                    newsService.delete(news);
            }
            sleep(8f);
        }
    }

    @Override
    public String cleanupTitle(String title) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasOccurred(String fullTitle, String href) {
        throw new UnsupportedOperationException();
    }
}
