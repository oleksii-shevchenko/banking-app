package ua.training.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

class LocaleUtil {
    static Locale getLocale(HttpServletRequest request) {
        String localeTag = (String) request.getSession().getAttribute("lang");
        return new Locale(localeTag.substring(0, 2), localeTag.substring(3, 5));
    }
}
