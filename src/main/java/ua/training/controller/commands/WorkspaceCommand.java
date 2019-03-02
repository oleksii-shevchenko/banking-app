package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.training.controller.util.managers.PathManager;

import javax.servlet.http.HttpServletRequest;

@Controller("workspace")
public class WorkspaceCommand implements Command {
    private PathManager pathManager;

    @Override
    public String execute(HttpServletRequest request) {
        return pathManager.getPath("path.workspace");
    }

    @Autowired
    public void setPathManager(PathManager pathManager) {
        this.pathManager = pathManager;
    }
}
