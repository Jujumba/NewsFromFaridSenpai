package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.logic.Starter;
import dev.jujumba.newsfromfaridsenpai.models.User;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @GetMapping("/auth/signin")
    public String signin() {
        return "auth/signin.html";
    }

    @GetMapping("/auth/signup")
    public String signup(@ModelAttribute("User") User user) {
        return "auth/signup.html";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication(); //fetches the user from current session
        String name = auth.getName();
        model.addAttribute("name", name);
        return "profile.html";
    }
}
