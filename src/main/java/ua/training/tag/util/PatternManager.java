package ua.training.tag.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class PatternManager {
    public static String getPattern(String key, Locale locale) {
        return ResourceBundle.getBundle("patterns", locale).getString(key);
    }
}
