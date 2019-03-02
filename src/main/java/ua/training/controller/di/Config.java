package ua.training.controller.di;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ua.training.controller.commands.Command;
import ua.training.model.entity.Request;

import javax.sql.DataSource;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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
    public DataSource dataSource() {
        BasicDataSource poolingSource = new BasicDataSource();
        poolingSource.setDriverClassName("${db.connection.driver}");
        poolingSource.setUrl("${db.connection.url}");
        poolingSource.setUsername("${db.connection.user}");
        poolingSource.setPassword("${db.connection.pass}");
        poolingSource.setMaxIdle(Integer.parseInt("${db.connection.idle.max}"));
        poolingSource.setMinIdle(Integer.parseInt("${db.connection.idle.min}"));
        return poolingSource;
    }
}
