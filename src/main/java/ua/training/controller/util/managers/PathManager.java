package ua.training.controller.util.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ResourceBundle;

/**
 * Util designed to getting paths to jsp from cached resource bundle.
 * @author Oleksii Shevchenko
 */
@Component
public class PathManager {
    private final ResourceBundle paths;

    @Autowired
    public PathManager(@Qualifier("paths") ResourceBundle paths) {
        this.paths = paths;
    }

    public String getPath(String key) {
        return paths.getString(key);
    }
}
