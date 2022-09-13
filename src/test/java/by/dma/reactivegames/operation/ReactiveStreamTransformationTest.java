package by.dma.reactivegames.operation;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Transforming and Filtering Reactive Streams.
 *
 * @author dzmitry.marudau
 * @since 2022.09
 */
class ReactiveStreamTransformationTest {

    @Test
    void fluxSkipByNumberOfElements() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> result = cities.skip(2);
        StepVerifier.create(result)
                .expectNext("Riga")
                .expectNext("Tallinn")
                .expectNext("Vilnius")
                .verifyComplete();
    }

    @Test
    void fluxSkipByDuration() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> result = cities.skip(Duration.ofSeconds(5));
        StepVerifier.create(result)
                .expectNext("Vilnius")
                .verifyComplete();
    }

    @Test
    void fluxTakeByNumberOfElements() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> result = cities.take(2);
        StepVerifier.create(result)
                .expectNext("Kiev", "Warsaw")
                .verifyComplete();
    }

    @Test
    void fluxTakeByDuration() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> result = cities.take(Duration.ofMillis(2500));
        StepVerifier.create(result)
                .expectNext("Kiev", "Warsaw")
                .verifyComplete();
    }

    @Test
    void fluxFilter() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofMillis(100));
        Flux<String> result = cities.filter(city -> city.contains("i"));
        StepVerifier.create(result)
                .expectNext("Kiev", "Riga", "Tallinn", "Vilnius")
                .verifyComplete();
    }

    @Test
    void fluxDistinct() {
        Flux<String> cities = Flux.just("Vilnius", "Kiev", "Kiev", "Riga", "Riga", "Vilnius")
                .delayElements(Duration.ofMillis(100));
        Flux<String> result = cities.distinct();
        StepVerifier.create(result)
                .expectNext("Vilnius", "Kiev", "Riga")
                .verifyComplete();
    }

    @Test
    void fluxMap() {
        Flux<String> citiesWithPopulation = Flux.just(
                        "Kiev has 2880000 people",
                        "Warsaw has 1765000 people",
                        "Riga has 632000 people",
                        "Tallinn has 426000 people",
                        "Vilnius has 542000 people")
                .delayElements(Duration.ofMillis(100));
        Flux<String> result = citiesWithPopulation.map(s -> {
            // emulate long-running processing
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String[] split = s.split("\\W");
            return split[2] + " citizens in " + split[0];
        });
        Duration duration = StepVerifier.create(result)
                .expectNext("2880000 citizens in Kiev")
                .expectNext("1765000 citizens in Warsaw")
                .expectNext("632000 citizens in Riga")
                .expectNext("426000 citizens in Tallinn")
                .expectNext("542000 citizens in Vilnius")
                .verifyComplete();
        assertThat(duration).isBetween(Duration.ofMillis(850), Duration.ofMillis(1000));
    }

    @Test
    void fluxFlatMap() {
        Flux<String> citiesWithPopulation = Flux.just(
                        "Kiev has 2880000 people",
                        "Warsaw has 1765000 people",
                        "Riga has 632000 people",
                        "Tallinn has 426000 people",
                        "Vilnius has 542000 people")
                .delayElements(Duration.ofMillis(100));
        Flux<String> result = citiesWithPopulation.flatMap(str -> Mono.just(str)
                .map(s -> {
                    // emulate long-running processing
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    String[] split = s.split("\\W");
                    return split[2] + " citizens in " + split[0];
                })
                .subscribeOn(Schedulers.parallel())
        );
        var resultStrings = Arrays.asList(
                "2880000 citizens in Kiev",
                "1765000 citizens in Warsaw",
                "632000 citizens in Riga",
                "426000 citizens in Tallinn",
                "542000 citizens in Vilnius"
        );
        Duration duration = StepVerifier.create(result)
                .expectNextMatches(resultStrings::contains)
                .expectNextMatches(resultStrings::contains)
                .expectNextMatches(resultStrings::contains)
                .expectNextMatches(resultStrings::contains)
                .expectNextMatches(resultStrings::contains)
                .verifyComplete();
        assertThat(duration).isBetween(Duration.ofMillis(600), Duration.ofMillis(800));
    }
}