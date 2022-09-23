package by.dma.reactivegames.exercises;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.reactivestreams.Subscription;

import by.dma.reactivegames.sources.ReactiveSources;
import by.dma.reactivegames.sources.User;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Use ReactiveSources to do the exercise.
 *
 * @author dzmitry.marudau
 * @since 2022.07
 */
public class ReactiveSourceOperations {

    private static final String LINE_DELIMITER = "#".repeat(20);

    public static void main(String... args) throws IOException {
        // playWithIntNumbersAndUserFlux();

        // playWithIntNumberMonoAndUserMono();

        // playWithErrorAndCompletionHooks();

        playWithUnresponsiveFluxAndMono();

        // playWithIntNumbersFluxAndIntNumbersFluxWithRepeat();

        // playWithIntNumbersFluxWithException();

        //playWithTransformation();

        System.out.println("# Press a key to end");
        System.in.read();
    }

    private static void playWithTransformation() {
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFlux transformation", LINE_DELIMITER);
        // Print size of intNumbersFlux after the last item returns
        System.out.println("# Print size of intNumbersFlux after the last item returns");
        ReactiveSources.intNumbersFlux()
                .count()
                .subscribe(size -> System.out.printf("intNumbersFlux final size: %d%n", size));

        // Collect all items of intNumbersFlux into a single list and print it
        System.out.println("# Collect all items of intNumbersFlux into a single list and print it");
        ReactiveSources.intNumbersFlux()
                .collectList()
                .subscribe(list -> System.out.printf("intNumbersFlux final list: %s%n", list));

        // Transform to a sequence of sums of adjacent two numbers
        System.out.println("# # Transform to a sequence of sums of adjacent two numbers");
        ReactiveSources.intNumbersFlux()
                .buffer(2)
                .map(list -> list.get(0) + list.get(1))
                .subscribe(x -> System.out.println("two-numbers-adjusted: " + x));
    }

    private static void playWithIntNumbersFluxWithException() {
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFluxWithException", LINE_DELIMITER);
        // Print values from intNumbersFluxWithException and print a message when error happens
        // version 1: swallow the error
        ReactiveSources.intNumbersFluxWithException()
                .subscribe(System.out::println, throwable ->
                        System.out.printf("intNumbersFluxWithException(fallback error): %s%n", throwable.getMessage()));
        // version 2: do something, but do not stop the stream and swallow the exception
/*         ReactiveSources.intNumbersFluxWithException()
                .doOnError(throwable -> System.out.printf("intNumbersFluxWithException(doOnError): %s%n", throwable.getMessage()))
                .subscribe(System.out::println); */

        // Print values from intNumbersFluxWithException and continue on errors
        System.out.println("# Print values from intNumbersFluxWithException and continue on errors");
        ReactiveSources.intNumbersFluxWithException()
                .onErrorContinue((throwable, element) ->
                        System.out.printf("intNumbersFluxWithException onErrorContinue: %s%n", throwable.getMessage()))
                .subscribe(System.out::println);

        // Print values from intNumbersFluxWithException and when errors happen, replace with a fallback sequence of -1 and -2
        System.out.println(
                "# Print values from intNumbersFluxWithException and when errors happen, replace with a fallback sequence of -1 and -2");
        ReactiveSources.intNumbersFluxWithException()
                .onErrorResume(throwable -> Flux.just(-1, -2))
                .subscribe(System.out::println);
    }

    private static void playWithIntNumbersFluxAndIntNumbersFluxWithRepeat() {
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFlux & intNumbersFluxWithRepeat", LINE_DELIMITER);
        // Print all values from intNumbersFlux that's greater than 5
        System.out.println("# Print all values from intNumbersFlux that's greater than 5");
        ReactiveSources.intNumbersFlux()
                .log()
                .filter(element -> element > 5)
                .subscribe(System.out::println);

        // Print 10 multiplied by each value from intNumbersFlux that's greater than 5
        System.out.println("# Print 10 multiplied by each value from intNumbersFlux that's greater than 5");
        ReactiveSources.intNumbersFlux()
                .filter(element -> element > 5)
                .map(element -> element * 10)
                .subscribe(System.out::println);

        // Print 10 multiplied by each value from intNumbersFlux for the first 3 numbers emitted that's greater than 5
        System.out.println(
                "# Print 10 multiplied by each value from intNumbersFlux for the first 3 numbers emitted that's greater than 5");
        ReactiveSources.intNumbersFlux()
                .filter(element -> element > 5)
                .map(element -> element * 10)
                .take(3)
                .subscribe(System.out::println);

        // Print each value from intNumbersFlux that's greater than 20. Print -1 if no elements are found
        System.out.println(
                "# Print each value from intNumbersFlux that's greater than 20. Print -1 if no elements are found");
        ReactiveSources.intNumbersFlux()
                .filter(element -> element > 20)
                .defaultIfEmpty(-1)
                .subscribe(System.out::println);

        // Switch ints from `intNumbersFlux` to the right user from `userFlux`
        System.out.println("# Switch ints from `intNumbersFlux` to the right user from `userFlux`");
        ReactiveSources.intNumbersFlux()
                .flatMap(element -> ReactiveSources.userFlux().filter(user -> user.id() == element).take(1))
                .subscribe(System.out::println);

        // Print only distinct numbers from `intNumbersFluxWithRepeat`
        System.out.println("# Print only distinct numbers from `intNumbersFluxWithRepeat`");
        ReactiveSources.intNumbersFluxWithRepeat()
                .distinct()
                .log()
                .subscribe(System.out::println);

        // Print from `intNumbersFluxWithRepeat` excluding immediately repeating numbers
        System.out.println("# Print from `intNumbersFluxWithRepeat` excluding immediately repeating numbers");
        ReactiveSources.intNumbersFluxWithRepeat()
                .distinctUntilChanged()
                .log()
                .subscribe(System.out::println);
    }

    private static void playWithUnresponsiveFluxAndMono() {
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "unresponsiveFlux & unresponsiveMono", LINE_DELIMITER);
        // Get the value from 'unresponsiveMono' into a String variable but give up after 5 seconds
        String stringFromUnresponsiveMono = ReactiveSources.unresponsiveMono()
                .doOnError(throwable -> System.out.printf("unresponsiveMono error: %s%n", throwable.getMessage()))
                .doOnCancel(() -> System.out.println("# unresponsiveMono cancelled"))
                .block(Duration.ofSeconds(5));
        System.out.println("stringFromUnresponsiveMono = " + stringFromUnresponsiveMono);

        // Get the value from 'unresponsiveFlux' into a String list but give up after 5 seconds(use Stream operators)
        List<String> listFromUnresponsiveFlux = ReactiveSources.unresponsiveFlux()
                .collectList()
                .block(Duration.ofSeconds(5));
        System.out.println("listFromUnresponsiveFlux = " + listFromUnresponsiveFlux);
    }

    private static void playWithErrorAndCompletionHooks() {
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "error and completion hooks", LINE_DELIMITER);
        // Subscribe to a flux using the error and completion hooks
        ReactiveSources.intNumbersFluxWithException()
                .subscribe(
                        element -> System.out.printf("consume intNumbersFluxWithException element: %d%n", element),
                        error -> System.out.printf("intNumbersFluxWithException error: %s%n", error.getMessage()),
                        () -> System.out.println("# intNumbersFluxWithException completed")
                );

        // Subscribe to a flux using an implementation of BaseSubscriber
        ReactiveSources.intNumbersFluxWithException()
                .subscribe(new MyCustomSubscriber<>());
    }

    private static void playWithIntNumberMonoAndUserMono() {
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumberMono & userMono", LINE_DELIMITER);
        // Print the value from `intNumberMono` when it emits
        Mono<Integer> integerMono = ReactiveSources.intNumberMono();
        integerMono.subscribe(element -> System.out.printf("consume integerMono element: %d%n", element));

        // Get the value from the Mono(`intNumberMono`) into an integer variable
        Integer number = ReactiveSources.intNumberMono().block();
        System.out.printf("integer variable from mono: %d%n", number);
        ReactiveSources.intNumberMono().blockOptional().ifPresent(System.out::println);
    }

    private static void playWithIntNumbersAndUserFlux() {
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFlux & userFlux", LINE_DELIMITER);
        // Print all numbers in the `intNumbersFlux` stream
        Flux<Integer> integerPublisher = ReactiveSources.intNumbersFlux();
        integerPublisher.subscribe(element -> System.out.println("# consume integerFlux element:" + element));
        integerPublisher.subscribe(element -> System.out.println("# consume again integerFlux element:" + element));

        // Print all users in the `userFlux` stream
        Flux<User> userPublisher = ReactiveSources.userFlux();
        userPublisher.subscribe(user -> System.out.println("# userFlux element:" + user));

        // Get all numbers in the `intNumbersFlux` stream into a List and print the list and its size
/*         List<Integer> integerList = ReactiveSources.intNumbersFlux()
                .transform(Flux::collectList)
                .blockFirst(); */
        List<Integer> integerList = ReactiveSources.intNumbersFlux()
                .log()
                .toStream()
                .toList();
        System.out.printf("integerList[size=%d] = %s%n", integerList.size(), integerList);
    }
}

class MyCustomSubscriber<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("# hookOnSubscribe");
        request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println(value.toString() + " received");
        request(1);
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        System.out.println("# hookOnError:" + throwable.getMessage());
    }
}