package dev.jujumba.newsfromfaridsenpai.logic.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jujumba
 */
@Component
public class Gpt {
    @Value("${dev.jujumba.gpt_api_key}")
    private String apiKey;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AtomicInteger counter = new AtomicInteger(0);
    private final ObjectMapper mapper = new ObjectMapper();
    @SneakyThrows
    String process(String text) {
        if (counter.incrementAndGet() > 60) {
            logger.warn("The request limit per minute has been reached!");
            Thread.sleep(60000);
            counter.set(0);
        }

        text = text.replace("\n","");

        String finalText = text;
        Map values = new HashMap();

        values.put("model","text-davinci-003");
        values.put("prompt", finalText +"\nMake a title out of this text");
        values.put("max_tokens",30);
        values.put("temperature",0.45f);


        String requestBody = mapper.writeValueAsString(values);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .header("Authorization", apiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<String, String> map;

        try {
            //todo: refactor...
            map = mapper.readValue(response.body().split("\\[")[1].split("]")[0], HashMap.class);
        } catch (Exception e) {
            logger.error(response.body());
            return null;
        }
        return map.get("text");
    }
}
