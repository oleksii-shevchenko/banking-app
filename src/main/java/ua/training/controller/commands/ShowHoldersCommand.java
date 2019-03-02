package ua.training.controller.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller("showHolders")
public class ShowHoldersCommand implements Command {
    private static Logger logger = LogManager.getLogger(ShowHoldersCommand.class);

    private UserService userService;
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("accountId"));

        User user = userService.get((Long) request.getSession().getAttribute("id"));

        if (!user.getAccounts().contains(accountId)) {
            logger.warn("User " + user.getId() + "try to access account " + accountId + " without permissions");

            return "redirect:" + pathManager.getPath("path.error");
        }

        request.setAttribute("masterAccount", accountId);
        request.setAttribute("permission", userService.getPermission((Long) request.getSession().getAttribute("id"), accountId));
        request.setAttribute("holders", userService.getAccountHolders(accountId));

        return pathManager.getPath("path.holders");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
