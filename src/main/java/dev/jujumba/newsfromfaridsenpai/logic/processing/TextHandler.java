package dev.jujumba.newsfromfaridsenpai.logic.processing;

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

    public synchronized String handleTitle(String title) {
        return gpt.process(translator.translate("EN",title));
    }
}
