package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ShowRequestsCommand implements Command {
    private final int ITEMS_NUMBER = 5;

    @Override
    public String execute(HttpServletRequest request) {
        int page = Objects.isNull(request.getParameter("page")) ? 1 : Integer.parseInt(request.getParameter("page"));

        request.setAttribute("page", new AccountService(JdbcDaoFactory.getInstance()).getRequestsPage(ITEMS_NUMBER, page));

        return PathManager.getPath("path.all-requests");
    }
}
