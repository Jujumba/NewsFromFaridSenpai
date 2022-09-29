package dev.jujumba.newsfromfaridsenpai.logic.translate;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Translator {
    @Value("translate_api_key")
    private String apiKey;

    @SneakyThrows
    public String translate(String languageCode, String... text) {
        Translate t = new Translate.Builder(
                GoogleNetHttpTransport.newTrustedTransport()
                , GsonFactory.getDefaultInstance(), null)
                .setApplicationName("NewsFromFaridSenpai")
                .build();
        Translate.Translations.List list = t.new Translations().list(Arrays.asList(text), languageCode);
        list.setKey(apiKey);
        TranslationsListResponse response = list.execute();
        StringBuilder result = new StringBuilder();
        for (var translationsResource : response.getTranslations()) {
            result.append(translationsResource.getTranslatedText());
        }
        return result.toString();
    }
}
