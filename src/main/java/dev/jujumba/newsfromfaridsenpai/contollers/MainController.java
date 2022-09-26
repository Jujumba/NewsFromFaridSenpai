package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.parsers.PresidentOffice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller()
@RequestMapping("/news")
public class MainController {
    private final PresidentOffice presidentOffice;
    @Autowired
    public MainController(PresidentOffice presidentOffice) {
        this.presidentOffice = presidentOffice;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("allNews", presidentOffice.parse());
        return "index.html";
    }
}
