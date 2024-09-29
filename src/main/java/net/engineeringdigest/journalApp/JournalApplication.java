package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);
        ConfigurableEnvironment env = context.getEnvironment();
        System.out.println(env.getActiveProfiles()[0]);
//    @Bean
//    public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
//        return new MongoTransactionManager(dbFactory);
//    }

    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}