package controllers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
public class Config {
    @Bean
    public play.mvc.Security.AuthenticatedAction security$AuthenticatedAction() {
        return new play.mvc.Security.AuthenticatedAction();
    }

    @Bean
    public UsernameValidator usernameValidator() {
        return new UsernameValidator(new Authenticator());
    }

    @Bean
    public Authenticator authenticator() {
        return new Authenticator();
    }
}
