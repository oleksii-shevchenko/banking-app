package ua.training.controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ua.training.model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller("openAccount")
public class OpenAccountCommand implements Command {
    private Map<Request.Type, Command> subCommands;

    @Override
    public String execute(HttpServletRequest request) {
        return subCommands.get(Request.Type.valueOf(request.getParameter("requestType"))).execute(request);
    }

    @Autowired
    @Qualifier("subCommands")
    public void setSubCommands(Map<Request.Type, Command> subCommands) {
        this.subCommands = subCommands;
    }
}
