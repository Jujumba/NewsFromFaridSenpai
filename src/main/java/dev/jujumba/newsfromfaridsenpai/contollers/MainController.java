package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.logic.Collector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/news")
public class MainController {
    private final Collector collector;
        public MainController(Collector collector) {
        collector.collect();
        this.collector = collector;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("all_news", collector.getAllNews());
        return "index.html";
    }
}
