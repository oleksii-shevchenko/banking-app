package ua.training.tag.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class FormatManager {
    private static ResourceBundle bundle = ResourceBundle.getBundle("formats");

    public static String getFormat(String key) {
        return bundle.getString(key);
    }

    public static String getLocalizedMessage(String key, Locale locale) {
        return ResourceBundle.getBundle("content", locale).getString(key);
    }

    public static String mapFormat(String key, Object... values) {
        return String.format(getFormat(key), values);
    }
}
