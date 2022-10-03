package by.dma.reactivegames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveGamesApplication {

    public static void main(String[] args) {
        //var applicationContext = SpringApplication.run(ReactiveGamesApplication.class, args);
        // System.out.println("applicationContext.getEnvironment() = " + applicationContext.getEnvironment());

        var app = new SpringApplication(ReactiveGamesApplication.class);
        app.run(args);
        System.out.println("springApplication.getWebApplicationType() = " + app.getWebApplicationType());

    }

}
