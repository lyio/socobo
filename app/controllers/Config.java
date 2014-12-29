package controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Bean
    public play.mvc.Security.AuthenticatedAction security$AuthenticatedAction() {
        return new play.mvc.Security.AuthenticatedAction();
    }
}
