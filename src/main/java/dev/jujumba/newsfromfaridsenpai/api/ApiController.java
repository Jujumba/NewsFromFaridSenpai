package dev.jujumba.newsfromfaridsenpai.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.jujumba.newsfromfaridsenpai.services.ApiKeysService;
import dev.jujumba.newsfromfaridsenpai.services.NewsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author Jujumba
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    private final ObjectMapper mapper = new ObjectMapper();
    {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.  WRITE_DATES_AS_TIMESTAMPS, false);
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
            return mapper.writeValueAsString(newsService.findAll().get(0));
        } else {
        return mapper.writeValueAsString(new HashMap() {{
            put("error","You didn't provide a correct API-key! " +
                    "Provide a correct API-key in an Authorization header ( \"Authorization\" :\"API-KEY\" )");
        }});
        }
    }
    @GetMapping
    @SneakyThrows
    public String returnNothing() {
        return mapper.writeValueAsString(new HashMap() {{
            put("error","You didn't provide a correct API-key! " +
                    "Provide a correct API-key in an Authorization header ( \"Authorization\" :\"API-KEY\" )");
        }});
    }

}
