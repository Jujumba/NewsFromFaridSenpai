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
    private PresidentOffice presidentOffice = null;
    {
        new Thread(() ->{
            presidentOffice.parse();
        }).start();
    }
    @Autowired
    public MainController(PresidentOffice presidentOffice) {
        this.presidentOffice = presidentOffice;
    }

    @GetMapping()
    public String index(Model model) {
        System.out.println(presidentOffice.getResult());
        model.addAttribute("all_news", presidentOffice.getResult());
        return "index.html";
    }
}
