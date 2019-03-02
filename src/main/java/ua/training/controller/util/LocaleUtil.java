package ua.training.controller.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Util parse locale from tag contained in user session.
 * @author Oleksii Shevchenko
 */
@Component
public class LocaleUtil {
    /**
     * Method returns current user locale contained in session.
     * @param request Http request for getting user session.
     * @return User locale.
     */
    public Locale getLocale(HttpServletRequest request) {
        return Locale.forLanguageTag((String) request.getSession().getAttribute("lang"));
    }
}
