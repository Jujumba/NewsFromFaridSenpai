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
    public void parse() {
        while (true) {
            logger.warn("Starting news cleaning");
            int today = LocalDateTime.now().getDayOfMonth();
            for (var news : newsService.findAll()) {
                int newsDay = news.getDateTime().getDayOfMonth();
                if (today - newsDay >= 3 || today - newsDay < 0)
                    newsService.delete(news);
            }
            sleep(12f);
        }
    }

    @Override
    public boolean hasOccurred(String href) {
        throw new UnsupportedOperationException();
    }
}
