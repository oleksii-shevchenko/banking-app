package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AddHolderCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User user = new UserService(JdbcDaoFactory.getInstance()).get((Long) request.getSession().getAttribute("id"));

        Long accountId = Long.valueOf(request.getParameter("accountId"));
        Long holderId = Long.valueOf(request.getParameter("holderId"));

        if (!user.getAccounts().contains(accountId)) {
            return "redirect:" + PathManager.getPath("path.error");
        }

        new UserService(JdbcDaoFactory.getInstance()).addHolder(holderId, accountId);

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
