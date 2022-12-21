package dev.jujumba.newsfromfaridsenpai.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.jujumba.newsfromfaridsenpai.api.ApiRequest;
import dev.jujumba.newsfromfaridsenpai.api.ApiResponse;
import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.models.exceptions.ApiException;
import dev.jujumba.newsfromfaridsenpai.services.ApiKeysService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jujumba
 */
@RestController
@RequestMapping("/api/v1")
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
    public ResponseEntity<String> getNews(@RequestHeader String Authorization, @RequestBody(required = false) ApiRequest request) {
        if (!apiKeysService.exists(Authorization)) {
            throw new ApiException();
        }
        List<News> allNews = newsService.findAll();

        String json = toJson(allNews.subList(0, Math.min(request.getAmount(), allNews.size())));

        return ResponseEntity.ok(json);
    }

    @GetMapping
    @SneakyThrows
    public ResponseEntity<String> index() {
        throw new ApiException();
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(ApiException apiException) {
        ApiResponse response = new ApiResponse(apiException);
        return new ResponseEntity<>(toJson(response), response.getStatus());
    }

    @SneakyThrows
    private String toJson(Object o) {
        return mapper.writeValueAsString(o);
    }
}