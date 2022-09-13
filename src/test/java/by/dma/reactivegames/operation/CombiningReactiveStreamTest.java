package by.dma.reactivegames.operation;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

/**
 * Combining Reactive Streams.
 *
 * @author dzmitry.marudau
 * @since 2022.09
 */
class CombiningReactiveStreamTest {

    @Test
    void fluxMerge() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofMillis(100));
        Flux<String> populationMillions = Flux.just(
                        "2.88", "1.765", "0.632", "0.426", "0.542")
                .delaySubscription(Duration.ofMillis(50))
                .delayElements(Duration.ofMillis(100));
        Flux<String> result = cities.mergeWith(populationMillions);
        // equivalen Flux.merge(cities, populationMillions)
        StepVerifier.create(result)
                .expectNext("Kiev")
                .expectNext("2.88")
                .expectNext("Warsaw")
                .expectNext("1.765")
                .expectNext("Riga")
                .expectNext("0.632")
                .expectNext("Tallinn")
                .expectNext("0.426")
                .expectNext("Vilnius")
                .expectNext("0.542")
                .verifyComplete();
    }

    @Test
    void fluxZip() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofMillis(100));
        Flux<Integer> populationThousands = Flux.just(
                        2_880_000, 1_765_000, 632_000, 426_000, 542_000)
                .delaySubscription(Duration.ofMillis(50))
                .delayElements(Duration.ofMillis(100));
        Flux<Tuple2<String, Integer>> result = Flux.zip(cities, populationThousands);
        StepVerifier.create(result)
                .expectNextMatches(tuple -> tuple.getT1().equals("Kiev") && tuple.getT2() == 2_880_000)
                .expectNextMatches(tuple -> tuple.getT1().equals("Warsaw") && tuple.getT2() == 1_765_000)
                .expectNextMatches(tuple -> tuple.getT1().equals("Riga") && tuple.getT2() == 632_000)
                .expectNextMatches(tuple -> tuple.getT1().equals("Tallinn") && tuple.getT2() == 426_000)
                .expectNextMatches(tuple -> tuple.getT1().equals("Vilnius") && tuple.getT2() == 542_000)
                .verifyComplete();
    }

    @Test
    void fluxZipWithFunctions() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delayElements(Duration.ofMillis(100));
        Flux<Integer> populationThousands = Flux.just(
                        2_880_000, 1_765_000, 632_000, 426_000, 542_000)
                .delaySubscription(Duration.ofMillis(50))
                .delayElements(Duration.ofMillis(100));
        Flux<String> result = Flux.zip(cities, populationThousands,
                (town, population) -> town + " has " + population + " people");
        StepVerifier.create(result)
                .expectNext("Kiev has 2880000 people")
                .expectNext("Warsaw has 1765000 people")
                .expectNext("Riga has 632000 people")
                .expectNext("Tallinn has 426000 people")
                .expectNext("Vilnius has 542000 people")
                .verifyComplete();
    }

    @Test
    void fluxFirstWithSignal() {
        Flux<String> cities = Flux.just("Kiev", "Warsaw", "Riga", "Tallinn", "Vilnius")
                .delaySubscription(Duration.ofMillis(200))
                .delayElements(Duration.ofMillis(50));
        Flux<String> populationMillions = Flux.just(
                        "2.88", "1.765", "0.632", "0.426", "0.542")
                .delaySubscription(Duration.ofMillis(50))
                .delayElements(Duration.ofMillis(100));
        Flux<String> result = Flux.firstWithSignal(cities, populationMillions);
        StepVerifier.create(result)
                .expectNext("2.88")
                .expectNext("1.765")
                .expectNext("0.632")
                .expectNext("0.426")
                .expectNext("0.542")
                .verifyComplete();
    }
}