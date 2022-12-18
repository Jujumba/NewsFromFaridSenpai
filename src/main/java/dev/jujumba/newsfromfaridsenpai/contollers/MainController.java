package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.logic.Starter;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jujumba
 */
@Controller()
@RequestMapping("/")
public class MainController {
    private final NewsService newsService;
    @Autowired
    public MainController(Starter collector, NewsService newsService) {
        this.newsService = newsService;
        collector.collect();
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("all_news", newsService.findAll());
        return "index.html";
    }
}
