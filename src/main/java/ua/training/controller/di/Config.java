package ua.training.controller.di;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.training.controller.commands.Command;
import ua.training.model.entity.Request;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Configuration
@ComponentScan(basePackages = "ua.training")
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
}
