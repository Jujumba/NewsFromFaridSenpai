package dev.jujumba.newsfromfaridsenpai.logic.processing;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jujumba
 */
@Component
public class TextHandler {
    private final Gpt gpt;
    private final Translator translator;
    @Autowired
    public TextHandler(Gpt gpt, Translator translator) {
        this.gpt = gpt;
        this.translator = translator;
    }

    @SneakyThrows
    public synchronized String handleTitle(String title) {
        Thread.sleep(2000);
        return gpt.process(translator.translate("EN",title));
    }
}
