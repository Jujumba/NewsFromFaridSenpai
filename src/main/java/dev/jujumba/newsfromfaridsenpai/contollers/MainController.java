package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.model.News;
import dev.jujumba.newsfromfaridsenpai.parsers.PresidentOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/news")
public class MainController {
    private static volatile List<News> allNews;
    private final PresidentOffice presidentOffice;
    @Autowired
    public MainController(PresidentOffice presidentOffice) {
        this.presidentOffice = presidentOffice;
    }

    @GetMapping()
    public String index(Model model) {
        allNews = presidentOffice.parse();
        model.addAttribute("all_news", allNews);
        return "index.html";
    }

    public static void setAllNews(List<News> allNews) {
        MainController.allNews = allNews;
    }
}
