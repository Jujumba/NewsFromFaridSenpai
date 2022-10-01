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
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Jujumba
 */
@Component
public class Gpt {
    @Value("${gpt_api_key}")
    private String apiKey;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int counter = 0;
    @SneakyThrows
    protected String process(String text) {
        if (++counter >= 60) {
            logger.warn("The request limit per minute has been reached!");
            int minute = LocalTime.now().getMinute();
            while (minute == minute) {
                Thread.sleep(500);
                minute = LocalTime.now().getMinute();
            }
        }
        Map values = new HashMap() {{
            put("model","text-davinci-002");
            put("prompt", text.replace("\n","") +"\nMake a title out of this text");
            put("temperature",0.45f);
        }};
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(values);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/completions"))
                .header("Authorization", apiKey)
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<String, String> map = null;
        try {
            map = mapper.readValue(response.body().split("\\[")[1].split("]")[0], HashMap.class);
        } catch (Exception e) {
            logger.error(response.body());
            e.printStackTrace();
        }
        return map.get("text").replace(".","").replace("\n","").replace(":","")
                .replace("!","");
    }
}