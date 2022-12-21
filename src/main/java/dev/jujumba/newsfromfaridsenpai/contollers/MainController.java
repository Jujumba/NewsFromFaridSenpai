package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.models.User;
import dev.jujumba.newsfromfaridsenpai.models.validation.UserValidator;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import dev.jujumba.newsfromfaridsenpai.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author Jujumba
 */
@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {
    private final NewsService newsService;
    private final UserValidator userValidator;
    private final UserService userService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("all_news", newsService.findAll());
        return "index.html";
    }
    @GetMapping("/auth/signin")
    public String signIn() {
        return "auth/signin.html";
    }

    @GetMapping("/auth/signup")
    public String signUp(@ModelAttribute("user") User user) {
        return "auth/signup.html";
    }

    @PostMapping("/auth/signup")
    public String performSignUp(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }
        userService.save(user);

        return "redirect:/auth/signin";
    }
    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication(); //fetches the user from current session
        String name = auth.getName();
        model.addAttribute("name", name);
        return "profile.html";
    }
}
