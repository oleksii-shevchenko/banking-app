package ua.training.controller.di;

import com.google.gson.Gson;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import ua.training.controller.commands.Command;
import ua.training.model.dao.mapper.Mapper;
import ua.training.model.dao.mapper.jdbc.JdbcCreditAccountMapper;
import ua.training.model.dao.mapper.jdbc.JdbcDepositAccountMapper;
import ua.training.model.entity.Account;
import ua.training.model.entity.Request;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Configuration
@ComponentScan(basePackages = "ua.training")
@PropertySource("database.properties")
public class Config {
    @Bean("paths")
    public ResourceBundle paths() {
        return ResourceBundle.getBundle("path");
    }

    @Bean("contentBundlesMap")
    public Map<Locale, ResourceBundle> contentBundlesMap(@Value("content") String bundle) {
        return Map.of(
                Locale.forLanguageTag("uk-UA"), ResourceBundle.getBundle(bundle, Locale.forLanguageTag("uk-UA")),
                Locale.forLanguageTag("en-US"), ResourceBundle.getBundle(bundle, Locale.forLanguageTag("en-US")));
    }

    @Bean("subCommands")
    public Map<Request.Type, Command> subCommands(@Qualifier("creditAccount") Command creditAccount, @Qualifier("depositAccount") Command depositAccount) {
        return Map.of(
                Request.Type.CREATE_CREDIT_ACCOUNT, creditAccount,
                Request.Type.CREATE_DEPOSIT_ACCOUNT, depositAccount
        );
    }

    @Bean("dataSource")
    @Lazy
    @Autowired
    public DataSource dataSource(Environment environment) {
        BasicDataSource poolingSource = new BasicDataSource();
        poolingSource.setDriverClassName(environment.getProperty("db.connection.driver"));
        poolingSource.setUrl(environment.getProperty("db.connection.url"));
        poolingSource.setUsername(environment.getProperty("db.connection.user"));
        poolingSource.setPassword(environment.getProperty("db.connection.pass"));
        poolingSource.setMaxIdle(Integer.parseInt(environment.getProperty("db.connection.idle.max")));
        poolingSource.setMinIdle(Integer.parseInt(environment.getProperty("db.connection.idle.min")));
        return poolingSource;
    }

    @Bean("queries")
    public ResourceBundle queries() {
        return ResourceBundle.getBundle("query");
    }

    @Bean("jdbcSubMappers")
    public Map<String, Mapper<Account>> jdbcSubMappers(JdbcDepositAccountMapper depositMapper, JdbcCreditAccountMapper creditMapper) {
        return Map.of(
                "CreditAccount", creditMapper,
                "DepositAccount", depositMapper
        );
    }

    @Bean("standardScheduledExecutor")
    public ScheduledExecutorService standardScheduledExecutor() {
        return Executors.newScheduledThreadPool(5);
    }

    @Bean("jsonMapper")
    public Gson jsonMapper() {
        return new Gson();
    }

    @Bean("fixerConfig")
    public ResourceBundle fixerConfig() {
        return ResourceBundle.getBundle("fixer_io");
    }

    @Bean("lock")
    @Scope("prototype")
    public ReadWriteLock lock() {
        return new ReentrantReadWriteLock();
    }
}
