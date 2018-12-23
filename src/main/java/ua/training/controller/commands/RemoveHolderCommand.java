package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.entity.Permission;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RemoveHolderCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        UserService service = new UserService(JdbcDaoFactory.getInstance());

        Long accountId = Long.valueOf(request.getParameter("accountId"));

        if (Objects.isNull(request.getParameter("holderId"))) {
            service.removeHolder((Long) request.getSession().getAttribute("id"), accountId);
            return "redirect:" + PathManager.getPath("path.completed");
        }

        if (service.getPermission((Long) request.getSession().getAttribute("id"), accountId).equals(Permission.RESTRICTED)) {
            return "redirect:" + PathManager.getPath("path.error");
        }

        service.removeHolder(Long.valueOf(request.getParameter("holderId")), accountId);

        return "redirect:" + PathManager.getPath("path.completed");
    }
}
