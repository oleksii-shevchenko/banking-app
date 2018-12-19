package ua.training.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class RegexUtil {
    boolean validate(String target, String regexKey, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("regex", locale);

        return target.matches(bundle.getString(regexKey));
    }
}
