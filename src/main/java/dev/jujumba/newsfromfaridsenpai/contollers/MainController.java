package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.logic.Collector;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
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
    private final Collector collector;
    private final NewsService newsService;

        public MainController(Collector collector, NewsService newsService) {
            this.newsService = newsService;
            this.collector = collector;
            this.collector.collect();
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("all_news", newsService.findAll());
        return "index.html";
    }
}
