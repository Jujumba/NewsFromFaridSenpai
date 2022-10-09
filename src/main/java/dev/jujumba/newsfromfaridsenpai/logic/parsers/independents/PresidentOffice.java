package dev.jujumba.newsfromfaridsenpai.logic.parsers.independents;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.Getter;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * @author Jujumba
 */
@Component
public class PresidentOffice extends AbstractParser {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm");
    @Autowired
    public PresidentOffice(TextHandler textHandler, NewsService newsService) {
        super(textHandler,newsService);
        setUrl("https://www.president.gov.ua/news/last");
    }
    @Override
    public void run() {
        parse();
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements withAAttr = execQuery(".item_stat_headline");
            for (var elem : withAAttr) {
                String title = elem.getElementsByTag("h3").text();

                String href = elem.getElementsByTag("a").attr("href");
                if (hasOccurred(href)) {
                    logger.warn("Will parse again in 3 minutes");
                    sleep(getDelay());
                    continue label;
                }

                String fullTitle = title;
                title = textHandler.handleTitle(title);
                String[] split = elem.text().split(" ");

                int day = Integer.parseInt(split[split.length - 6]);
                int year = Integer.parseInt(split[split.length - 4]);
                Month month = parseMonth(split[split.length - 5]);

                String[] hourAndMinute = split[split.length - 1].split(":");
                int hour = Integer.parseInt(hourAndMinute[0]);
                int minute = Integer.parseInt(hourAndMinute[1]);

                LocalDateTime time = LocalDateTime.of(year,month ,day, hour, minute);
                News news = new News(title, href, time, fullTitle);

                if (!newsService.save(news, time)) {
                    logger.info("New news found");
                }
            }
            sleep(getDelay());
        }
    }

    @Override
    public String cleanupTitle(String title) {
        return null;
    }

    private Month parseMonth(String s) {
        return switch (s) {
            case "січня" -> Month.JANUARY;
            case "лютого" -> Month.FEBRUARY;
            case "березня" -> Month.MARCH;
            case "квітня" -> Month.APRIL;
            case "травня" -> Month.MAY;
            case "червня" -> Month.JUNE;
            case "липня" -> Month.JULY;
            case "серпня" -> Month.AUGUST;
            case "вересня" -> Month.SEPTEMBER;
            case "жовтня" -> Month.OCTOBER;
            case "листопада" -> Month.NOVEMBER;
            case "грудня" -> Month.DECEMBER;
            default -> throw new RuntimeException("PARSE ERROR");
        };
    }
}
