package dev.jujumba.newsfromfaridsenpai.logic.cleaner;

import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
/**
 * @author Jujumba
 */
public class NewsCleaner implements Runnable {
    private final NewsService service;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public NewsCleaner(NewsService service) {
        this.service = service;
    }

    @Override
    public void run() {
        while (true) {
            logger.info("Starting news cleaning");
            for (var news : service.findAll())
                if (LocalDateTime.now().getDayOfMonth() - news.getNow().getDayOfMonth() >3) {
                    service.delete(news);
                }
            try {
                TimeUnit.HOURS.sleep(8);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
