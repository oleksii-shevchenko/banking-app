package ua.training.controller.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Util parse locale from tag contained in user session.
 * @author Oleksii Shevchenko
 */
public class LocaleUtil {
    /**
     * Method returns current user locale contained in session.
     * @param request Http request for getting user session.
     * @return User locale.
     */
    public static Locale getLocale(HttpServletRequest request) {
        return Locale.forLanguageTag((String) request.getSession().getAttribute("lang"));
    }
}
