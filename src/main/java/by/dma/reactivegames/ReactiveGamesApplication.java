package by.dma.reactivegames;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import by.dma.reactivegames.sources.ReactiveSources;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveGamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveGamesApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> applicationListener() {
        return event -> {
            System.out.println("OnApplicationReadyEvent processing....");
            var app = event.getSpringApplication();
            System.out.println("SpringApplication.webApplicationType: " + app.getWebApplicationType());
        };
    }

}

@Controller
@ResponseBody
class ReactiveRestController {

    @GetMapping("/")
    public Mono<String> hello() {
/*             return computeHello()
                    .zipWith(computeWorld(), (t1, t2) -> t1 + ", " + t2); */
        return computeHello()
                .zipWith(computeWorld())
                .map(tuple -> tuple.getT1() + ", " + tuple.getT2());
    }

    private static Mono<String> computeHello() {
        return Mono.just("Hello")
                .delayElement(Duration.ofSeconds(3));
    }

    private static Mono<String> computeWorld() {
        return Mono.just("World!")
                .delayElement(Duration.ofSeconds(3));
    }
}
