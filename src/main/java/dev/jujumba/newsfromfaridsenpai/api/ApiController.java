package dev.jujumba.newsfromfaridsenpai.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.ApiKeysService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @author Jujumba
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    private final ObjectMapper mapper = new ObjectMapper();
    private final NewsService newsService;
    private final ApiKeysService apiKeysService;
    @Autowired
    public ApiController(NewsService newsService, ApiKeysService apiKeysService) {
        this.newsService = newsService;
        this.apiKeysService = apiKeysService;
    }

    @SneakyThrows
    @PostMapping
    //TODO: Починить...(((((
    public String getLast(@RequestBody String apiKey) {
        if (apiKeysService.exists(apiKey)) {
            //TODO: вернуть напрямую последнюю новость, для этого сделать метод count в сервисе
            List<News> allNews = newsService.findAll();
            return mapper.writeValueAsString(allNews.get(allNews.size() - 1));
        } else {
        return mapper.writeValueAsString(new HashMap() {{
            put("error","You didn't provide a correct API-key!");
        }});
        }
    }
}
