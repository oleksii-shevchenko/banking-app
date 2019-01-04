package ua.training.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Simple realization of regex checking using localized regex from resource bundle.
 * @author Oleksii Shevchenko
 */
public class RegexUtil {
    /**
     * Checks is the targeted string matches the regex from resource bundle.
     * @param target String to check.
     * @param regexKey Key for getting regex from bundle.
     * @param locale Current locale.
     * @return Is string matches regex.
     */
    boolean validate(String target, String regexKey, Locale locale) {
        return target.matches(ResourceBundle.getBundle("regex", locale).getString(regexKey));
    }
}
