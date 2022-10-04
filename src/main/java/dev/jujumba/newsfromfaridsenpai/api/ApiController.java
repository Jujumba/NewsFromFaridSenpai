package dev.jujumba.newsfromfaridsenpai.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author Jujumba
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    private final ObjectMapper mapper;
    @Autowired
    public ApiController(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @SneakyThrows
    @GetMapping()
    public String getLast() {
        return mapper.writeValueAsString(new HashMap(){{
            put("Hello","World");
        }});
    }
}
