package ua.training.controller.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * This util used for parsing command from request to app api.
 * @author Oleksii Shevchenko
 */
@Component
public class CommandUtil {
    /**
     * Method extracts command from request uri. Only request to api allowed in this method.
     * @param request User request.
     * @return Command as string.
     */
    public String extractCommand(HttpServletRequest request) {
        return request.getRequestURI().replaceAll(".*/api/", "");
    }
}
