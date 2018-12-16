package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/jsp/error.jsp";
    }
}
