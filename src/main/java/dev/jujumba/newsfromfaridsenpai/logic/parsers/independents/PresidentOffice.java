package dev.jujumba.newsfromfaridsenpai.logic.parsers.independents;

import dev.jujumba.newsfromfaridsenpai.logic.parsers.AbstractParser;
import dev.jujumba.newsfromfaridsenpai.logic.processing.TextHandler;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static java.time.Month.*;
import static java.time.Month.DECEMBER;

/**
 * @author Jujumba
 */
@Component
public class PresidentOffice extends AbstractParser {
    @Autowired
    public PresidentOffice(TextHandler textHandler, NewsService newsService) {
        super(textHandler,newsService);
        setUrl("https://www.president.gov.ua/news/last");
    }

    @Override
    public void parse() {
        label: while (true) {
            connect();
            Elements selected = execQuery(".item_stat_headline");
            for (var elem : selected) {
                String title = textHandler.handleTitle(elem.getElementsByTag("h3").text());

                String href = elem.getElementsByTag("a").attr("href");
                if (hasOccurred(href)) {
                    logger.warn("Waiting 3 minutes, then`ll parse again");
                    sleep(getDelay());
                    continue label;
                }
                LocalDateTime dateTime = parseDate(elem.getElementsByTag("p").text());
                News news = new News(title, href, dateTime);
                newsService.save(news);
            }
            sleep(getDelay());
        }
    }
    private LocalDateTime parseDate(String date) {
        String[] split = date.split(" ");
        //21 грудня 2022 року - 08:01
        int day = Integer.parseInt(split[0]);
        Month month = parseMonth(split[1]);
        int year = Integer.parseInt(split[2]);
        return LocalDateTime.of(LocalDate.of(year,month, day), LocalTime.parse(split[5]));
    }

    private Month parseMonth(String split) {
        return switch (split) {
            case "січня" -> JANUARY;
            case "лютого" -> FEBRUARY;
            case "березня" -> MARCH;
            case "квітня" -> APRIL;
            case "травня" -> MAY;
            case "червня" -> JUNE;
            case "липня" -> JULY;
            case "серпня" -> AUGUST;
            case "вересеня" -> SEPTEMBER;
            case "жовтеня" -> OCTOBER;
            case "листопада" -> NOVEMBER;
            case "грудня" -> DECEMBER;
            default -> throw new RuntimeException();
        };
    }
}
