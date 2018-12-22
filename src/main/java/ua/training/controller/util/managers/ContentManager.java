package ua.training.controller.util.managers;

import ua.training.controller.util.LocaleUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class ContentManager {
    public static String getLocalizedContent(String key, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("content", locale);
        return bundle.containsKey(key) ? bundle.getString(key) : "";
    }

    public static void setLocalizedMessage(HttpServletRequest request, String attributeKey, String messageKey) {
        request.setAttribute(attributeKey, getLocalizedContent(messageKey,  LocaleUtil.getLocale(request)));
    }
}
