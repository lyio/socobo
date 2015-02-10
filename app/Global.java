import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate3.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import play.Application;
import play.GlobalSettings;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Application wide behaviour. We establish a Spring application context for the dependency injection system and
 * configure Spring Data.
 */
public class Global extends GlobalSettings {

    /**
     * The name of the persistence unit we will be using.
     */
    static final String DEFAULT_PERSISTENCE_UNIT = "defaultPersistenceUnit";

    /**
     * Declare the application context to be used - a Java annotation based application context requiring no XML.
     */
    final public AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    /**
     * Sync the context lifecycle with Play's.
     */
    @Override
    public void onStart(final Application app) {
        super.onStart(app);

        // AnnotationConfigApplicationContext can only be refreshed once, but we do it here even though this method
        // can be called multiple times. The reason for doing during startup is so that the Play configuration is
        // entirely available to this application context.
        ctx.register(SpringDataJpaConfiguration.class);
        ctx.scan("controllers", "models", "biz");
        ctx.refresh();

        // This will construct the beans and call any construction lifecycle methods e.g. @PostConstruct
        ctx.start();
    }

    /**
     * Sync the context lifecycle with Play's.
     */
    @Override
    public void onStop(final Application app) {
        // This will call any destruction lifecycle methods and then release the beans e.g. @PreDestroy
        ctx.close();

        super.onStop(app);
    }

    /**
 * This configuration establishes Spring Data concerns including those of JPA.
 */
@Configuration
@EnableJpaRepositories("datalayer")
public static class SpringDataJpaConfiguration {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        Config conf = ConfigFactory.load();
        return Persistence.createEntityManagerFactory(conf.getString("jpa.default"));
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
}
}
