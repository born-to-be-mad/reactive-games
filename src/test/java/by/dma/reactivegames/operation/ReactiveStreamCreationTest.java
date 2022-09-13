package by.dma.reactivegames.operation;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

/**
 * Created by IntelliJ IDEA.
 *
 * @author dzmitry.marudau
 * @since 2022.09
 */
class ReactiveStreamCreationTest {

    @Test
    void fluxJust() {
        var flux = ReactiveStreamCreation.fluxJust();
        StepVerifier.create(flux)
                .expectNext("Kiev")
                .expectNext("Warsaw")
                .expectNext("Vilnius")
                .expectNext("Riga")
                .expectNext("Tallinn")
                .expectNext("Minsk")
                .verifyComplete();
    }

    @Test
    void fluxFromArray() {
        var flux = ReactiveStreamCreation.fluxFromArray();
        StepVerifier.create(flux)
                .expectNext("Kiev")
                .expectNext("Warsaw")
                .expectNext("Vilnius")
                .expectNext("Riga")
                .expectNext("Tallinn")
                .expectNext("Minsk")
                .verifyComplete();
    }

    @Test
    void fluxFromIterable() {
        var flux = ReactiveStreamCreation.fluxFromIterable();
        StepVerifier.create(flux)
                .expectNext("Kiev")
                .expectNext("Warsaw")
                .expectNext("Vilnius")
                .expectNext("Riga")
                .expectNext("Tallinn")
                .expectNext("Minsk")
                .verifyComplete();
    }

    @Test
    void fluxFromStream() {
        var flux = ReactiveStreamCreation.fluxFromStream();
        StepVerifier.create(flux)
                .expectNext("Kiev")
                .expectNext("Warsaw")
                .expectNext("Vilnius")
                .expectNext("Riga")
                .expectNext("Tallinn")
                .expectNext("Minsk")
                .verifyComplete();
    }

    @Test
    void fluxRange() {
        var flux = ReactiveStreamCreation.fluxRange();
        StepVerifier.create(flux)
                .expectNext(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .verifyComplete();
    }

    @Test
    void fluxInterval() {
        var flux = ReactiveStreamCreation.fluxInterval();
        StepVerifier.create(flux)
                .expectNext(0L, 1L, 2L, 3L, 4L)
                .verifyComplete();
    }
}