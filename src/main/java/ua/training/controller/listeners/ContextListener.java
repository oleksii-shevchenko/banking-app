package ua.training.controller.listeners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.training.controller.commands.Command;
import ua.training.controller.di.Config;
import ua.training.controller.util.managers.PathManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Collections;

/**
 * This web listener used for starting services simultaneously with start of application.
 * @author Oleksii Shevhcenko
 */
@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        sce.getServletContext().setAttribute("commands", Collections.unmodifiableMap(context.getBeansOfType(Command.class)));
        sce.getServletContext().setAttribute("pathManager", context.getBean(PathManager.class));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}