package dev.jujumba.newsfromfaridsenpai.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.jujumba.newsfromfaridsenpai.api.apimodels.ApiRequest;
import dev.jujumba.newsfromfaridsenpai.api.errors.ApiError;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.ApiKeysService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public String getNews(@RequestHeader String Authorization,
                          @RequestBody(required = false) ApiRequest request) {
        if (!apiKeysService.exists(Authorization)) {
            return mapper.writeValueAsString(new ApiError());
        }
        List<News> news = new ArrayList<>();
        List<News> allNews = newsService.findAll();
        if (request.getAmount() >= allNews.size()) {
            return mapper.writeValueAsString(allNews);
        }
        for (int i = 0; i < request.getAmount(); i++) {
            news.add(allNews.get(i));
        }
        return mapper.writeValueAsString(news);
    }
    @GetMapping
    @SneakyThrows
    public String returnNothing() {
        return mapper.writeValueAsString(new ApiError());
    }
}