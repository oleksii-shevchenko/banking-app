package ua.training.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class ContentManager {
    public String getContent(String key, Locale locale) {
        return ResourceBundle.getBundle("content", locale).getString(key);
    }

    public void setMessage(HttpServletRequest request, String key, String messageKey) {
        String lang = (String) request.getSession().getAttribute("lang");
        Locale locale = new Locale(lang.substring(0, 2), lang.substring(3,5));
        request.setAttribute(key, getContent(messageKey, locale));
    }
}
