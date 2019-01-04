package ua.training.controller.util.managers;

import java.util.ResourceBundle;

/**
 * Util designed to getting paths to jsp from cached resource bundle.
 * @author Oleksii Shevchenko
 */
public class PathManager {
    private static ResourceBundle paths = ResourceBundle.getBundle("path");

    public static String getPath(String key) {
        return paths.getString(key);
    }
}
