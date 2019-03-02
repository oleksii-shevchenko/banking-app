package ua.training.controller.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ResourceBundle;

@Configuration
@ComponentScan(basePackages = "ua.training")
public class Config {
    @Bean("paths")
    ResourceBundle paths() {
        return ResourceBundle.getBundle("path");
    }
}
