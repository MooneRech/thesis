package lv.animelistapp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import java.util.Locale;

public final class Msg {


    //TODO: enviromental parameters in defaults
    public static String getMsg(String message){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        if (messageSource.getBasenameSet().isEmpty()) {
            messageSource.setBasenames("messages");
        }

        return messageSource.getMessage(message, null, Locale.getDefault());
    }

    public static void changeLocale(String code) {
        Locale.setDefault(Locale.forLanguageTag(code));
    }

}
