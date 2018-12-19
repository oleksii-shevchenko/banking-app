package ua.training.controller.commands;

import ua.training.controller.util.managers.PathManager;

import javax.servlet.http.HttpServletRequest;

public class WorkspaceCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PathManager.getPath("path.workspace");
    }
}
