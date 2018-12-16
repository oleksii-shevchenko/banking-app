package ua.training.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.ResourceBundle;

public class ContentManager {
    public String getContent(String key, Locale locale) {
        return ResourceBundle.getBundle("content", locale).getString(key);
    }

    public void setMessage(HttpServletRequest request, String key, String messageKey) {
        Locale locale = LocaleUtil.getLocale(request);
        request.setAttribute(key, getContent(messageKey, locale));
    }
}
