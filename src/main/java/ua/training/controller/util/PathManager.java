package ua.training.controller.util;

import java.util.ResourceBundle;

public class PathManager {
    private static ResourceBundle paths = ResourceBundle.getBundle("path");

    public static String getPath(String key) {
        return paths.getString(key);
    }
}
