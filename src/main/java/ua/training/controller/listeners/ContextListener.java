package ua.training.controller.listeners;

import ua.training.model.dao.factory.JdbcDaoFactory;
import ua.training.model.service.ScheduledTaskService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        new ScheduledTaskService().init(JdbcDaoFactory.getInstance());
        //new CurrencyExchangeService().init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}