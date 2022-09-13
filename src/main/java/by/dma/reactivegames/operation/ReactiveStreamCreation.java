package by.dma.reactivegames.operation;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import reactor.core.publisher.Flux;

/**
 * Reactive Stream Creation sa,ples.
 *
 * @author dzmitry.marudau
 * @since 2022.09
 */
public class ReactiveStreamCreation {

    public static Flux<String> fluxJust() {
        return Flux.just("Kiev", "Warsaw", "Vilnius", "Riga", "Tallinn", "Minsk");
    }

    public static Flux<String> fluxFromArray() {
        String[] cities = { "Kiev", "Warsaw", "Vilnius", "Riga", "Tallinn", "Minsk" };
        return Flux.fromArray(cities);
    }

    public static Flux<String> fluxFromIterable() {
        return Flux.fromIterable(List.of("Kiev", "Warsaw", "Vilnius", "Riga", "Tallinn", "Minsk"));
    }

    public static Flux<String> fluxFromStream() {
        return Flux.fromStream(Stream.of("Kiev", "Warsaw", "Vilnius", "Riga", "Tallinn", "Minsk"));
    }

    public static Flux<Integer> fluxRange() {
        return Flux.range(1, 10);
    }

    public static Flux<Long> fluxInterval() {
        return Flux.interval(Duration.ofSeconds(1))
                .take(5);
    }
}
