package dev.jujumba.newsfromfaridsenpai.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.jujumba.newsfromfaridsenpai.api.apimodels.ApiRequest;
import dev.jujumba.newsfromfaridsenpai.api.apimodels.ApiResponse;
import dev.jujumba.newsfromfaridsenpai.api.apimodels.exceptions.ApiException;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.services.ApiKeysService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
            throw new ApiException();
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
        throw new ApiException();
    }

    @SneakyThrows
    @ExceptionHandler
    public ResponseEntity<String> handleException(ApiException apiException) {
        ApiResponse response = new ApiResponse(apiException);
        return new ResponseEntity<>(mapper.writeValueAsString(response), response.getStatus());
    }
}