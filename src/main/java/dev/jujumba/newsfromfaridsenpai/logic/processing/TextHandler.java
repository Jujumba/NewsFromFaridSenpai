package dev.jujumba.newsfromfaridsenpai.logic.processing;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Jujumba
 */
@Component
public class TextHandler {
    @Value("${dev.jujumba.shall_process}")
    private boolean shallProcess;
    private final Gpt gpt;
    private final Translator translator;
    @Autowired
    public TextHandler(Gpt gpt, Translator translator) {
        this.gpt = gpt;
        this.translator = translator;
    }

    @SneakyThrows
    public String handleTitle(String title) {
        if (shallProcess) {
            return gpt.process(translator.translate("EN", title));
        }
        return title;
    }
}
