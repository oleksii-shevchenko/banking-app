package ua.training.controller.commands;

import org.springframework.stereotype.Controller;
import ua.training.model.entity.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller("openAccount")
public class OpenAccountCommand implements Command {
    private static Map<Request.Type, Command> subCommands = Map.of(
            Request.Type.CREATE_CREDIT_ACCOUNT, new CreditAccountCommand(),
            Request.Type.CREATE_DEPOSIT_ACCOUNT, new DepositAccountCommand()
    );

    @Override
    public String execute(HttpServletRequest request) {
        return subCommands.get(Request.Type.valueOf(request.getParameter("requestType"))).execute(request);
    }
}
