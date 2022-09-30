package dev.jujumba.newsfromfaridsenpai.logic.processing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
@Component
public class Gpt {
    @Value("${gpt_api_key}")
    private String apiKey;
    @SneakyThrows
    protected String process(String text) {
        Map values = new HashMap() {{
            put("model","text-curie-001");
            put("prompt", text +"\nMake a headline out of this text");
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
        } catch (JsonProcessingException e) {
            System.out.println(map);
            e.printStackTrace();
        }
        return map.get("text").replace(".","").replace("\n","").replace(":","")
                .replace("!","");
    }
}
