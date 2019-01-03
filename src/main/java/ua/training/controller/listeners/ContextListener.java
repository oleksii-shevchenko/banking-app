package ua.training.controller.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.CurrencyExchangeService;
import ua.training.model.service.ScheduledTaskService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    private static Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new ScheduledTaskService().init(JdbcDaoFactory.getInstance());
        logger.info("Scheduled task service is online.");

        new CurrencyExchangeService().init();
        logger.info("Currency exchange service is online.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}