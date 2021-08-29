package lv.animelistapp.utils;

import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public final class Msg {

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
