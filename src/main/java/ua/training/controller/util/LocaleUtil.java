package ua.training.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LocaleUtil {
    public static Locale getLocale(HttpServletRequest request) {
        return Locale.forLanguageTag((String) request.getSession().getAttribute("lang"));
    }
}
