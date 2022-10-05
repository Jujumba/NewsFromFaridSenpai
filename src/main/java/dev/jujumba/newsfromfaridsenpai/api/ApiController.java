package dev.jujumba.newsfromfaridsenpai.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.ApiKeysService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
    {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
    private final NewsService newsService;
    private final ApiKeysService apiKeysService;
    @Autowired
    public ApiController(NewsService newsService, ApiKeysService apiKeysService) {
        this.newsService = newsService;
        this.apiKeysService = apiKeysService;
    }

    @SneakyThrows
    @PostMapping
    public String getLast(@RequestHeader String Authorization) {
        if (apiKeysService.exists(Authorization)) {
            //TODO: вернуть напрямую последнюю новость, для этого сделать метод count в сервисе
            List<News> allNews = newsService.findAll();
            System.out.println(allNews.get(allNews.size() - 1));
            return mapper.writeValueAsString(allNews.get(allNews.size() - 1));
        } else {
        return mapper.writeValueAsString(new HashMap() {{
            put("error","You didn't provide a correct API-key!");
        }});
        }
    }
}
