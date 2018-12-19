package ua.training.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class RegexUtil {
    boolean validate(String target, String regexKey, Locale locale) {
        return target.matches(ResourceBundle.getBundle("regex", locale).getString(regexKey));
    }
}
