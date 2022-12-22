package dev.jujumba.newsfromfaridsenpai.contollers;

import dev.jujumba.newsfromfaridsenpai.models.ApiKey;
import dev.jujumba.newsfromfaridsenpai.models.User;
import dev.jujumba.newsfromfaridsenpai.models.validation.UserValidator;
import dev.jujumba.newsfromfaridsenpai.services.ApiKeysService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import dev.jujumba.newsfromfaridsenpai.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    private final ApiKeysService apiKeysService;

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
        User user = fetchUserFromSession();
        model.addAttribute("email", user.getEmail());
        model.addAttribute("keys", apiKeysService.findAllByUser(user));
        return "profile.html";
    }

    @PostMapping("/profile/create_apikey")
    public String createApiKey(ApiKey apikey) {
        User user = fetchUserFromSession();
        apikey.setUser(user);
        apiKeysService.save(apikey);
        return "redirect:/profile";
    }

    @PostMapping("/profile/remove_apikey/{id}")
    public String removeApiKey(@PathVariable("id") int id) {
        apiKeysService.remove(id);
        return "redirect:/profile";
    }

    private User fetchUserFromSession() {
        Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }
}
