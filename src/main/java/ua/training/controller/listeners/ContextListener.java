package ua.training.controller.listeners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ua.training.controller.di.Config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * This web listener used for starting services simultaneously with start of application.
 * @author Oleksii Shevhcenko
 */
@WebListener
public class ContextListener implements ServletContextListener {
    //private static Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        /*new ScheduledTaskService().init(JdbcDaoFactory.getInstance());

        new CurrencyExchangeService().init();
        logger.info("Scheduled task service is online.");
        logger.info("Currency exchange service is online.");*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}