package ua.training.controller.util;

import javax.servlet.http.HttpServletRequest;

public class CommandUtil {
    public String extractCommand(HttpServletRequest request) {
        return request.getRequestURI().replaceAll(".*/api/", "");
    }
}
