package dev.jujumba.newsfromfaridsenpai.logic.processing;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.model.TranslationsListResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * @author Jujumba
 */
@Component
public class Translator {
    @Value("${dev.jujumba.translate_api_key}")
    private String apiKey;
    private Translate t;
    {
        try {
            t = new Translate.Builder(
                    GoogleNetHttpTransport.newTrustedTransport()
                    ,GsonFactory.getDefaultInstance(), null)
                    .setApplicationName("NewsFromFaridSenpai")
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SneakyThrows
    String translate(String languageCode, String text) {
        Translate.Translations.List list = t.new Translations().list(Collections.singletonList(text), languageCode);
        list.setKey(apiKey);
        TranslationsListResponse response = list.execute();
        StringBuilder result = new StringBuilder();
        for (var translationsResource : response.getTranslations()) {
            result.append(translationsResource.getTranslatedText());
        }
        return result.toString();
    }
}
