package by.dma.reactivegames.exercises;

import java.io.IOException;
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
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFlux & userFlux", LINE_DELIMITER);
        // Print all numbers in the `intNumbersFlux` stream
        Flux<Integer> integerPublisher = ReactiveSources.intNumbersFlux();
        integerPublisher.subscribe(element -> System.out.println("consume integerFlux element:" + element));
        integerPublisher.subscribe(element -> System.out.println("consume again integerFlux element:" + element));

        // Print all users in the `userFlux` stream
        Flux<User> userPublisher = ReactiveSources.userFlux();
        userPublisher
                .subscribe(user -> System.out.println("userFlux element:" + user));

        // Get all numbers in the `intNumbersFlux` stream into a List and print the list and its size
/*         List<Integer> integerList = ReactiveSources.intNumbersFlux()
                .transform(Flux::collectList)
                .blockFirst(); */
        List<Integer> integerList = ReactiveSources.intNumbersFlux()
                .log()
                .toStream()
                .toList();
        System.out.printf("integerList[size=%d] = %s%n", integerList.size(), integerList);

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumberMono & userMono", LINE_DELIMITER);
        // Print the value from `intNumberMono` when it emits
        Mono<Integer> integerMono = ReactiveSources.intNumberMono();
        integerMono.subscribe(element -> System.out.printf("consume integerMono element: %d%n", element));

        // Get the value from the Mono(`intNumberMono`) into an integer variable
        Integer number = ReactiveSources.intNumberMono().block();
        System.out.printf("integer variable from mono: %d%n", number);
        ReactiveSources.intNumberMono().blockOptional().ifPresent(System.out::println);

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "error and completion hooks", LINE_DELIMITER);
        // Subscribe to a flux using the error and completion hooks
        ReactiveSources.intNumbersFluxWithException()
                .subscribe(
                        element -> System.out.printf("consume intNumbersFluxWithException element: %d%n", element),
                        error -> System.out.printf("intNumbersFluxWithException error: %s%n", error.getMessage()),
                        () -> System.out.println("intNumbersFluxWithException completed")
                );

        // Subscribe to a flux using an implementation of BaseSubscriber
        ReactiveSources.intNumbersFluxWithException()
                .subscribe(new MyCustomSubscriber<>());

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "unresponsiveFlux & unresponsiveMono", LINE_DELIMITER);
        // Get the value from 'unresponsiveMono' into a String variable but give up after 5 seconds
/*         String stringFromUnresponsiveMono = ReactiveSources.unresponsiveMono()
                .doOnError(throwable -> System.out.printf("unresponsiveMono error: %s%n", throwable.getMessage()))
                .doOnCancel(() -> System.out.println("unresponsiveMono cancelled"))
                .block(Duration.ofSeconds(5)); */

        // Get the value from 'unresponsiveFlux' into a String list but give up after 5 seconds(use Stream operators)
        //List<String> listFromUnresponsiveFlux = ReactiveSources.unresponsiveFlux().toStream().toList();

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFlux & intNumbersFluxWithRepeat", LINE_DELIMITER);

        // Print all values from intNumbersFlux that's greater than 5
        System.out.println("Print all values from intNumbersFlux that's greater than 5");
        ReactiveSources.intNumbersFlux()
                .log()
                .filter(element -> element > 5)
                .subscribe(System.out::println);

        // Print 10 multiplied by each value from intNumbersFlux that's greater than 5
        System.out.println("Print 10 multiplied by each value from intNumbersFlux that's greater than 5");
        ReactiveSources.intNumbersFlux()
                .filter(element -> element > 5)
                .map(element -> element * 10)
                .subscribe(System.out::println);

        // Print 10 multiplied by each value from intNumbersFlux for the first 3 numbers emitted that's greater than 5
        System.out.println("Print 10 multiplied by each value from intNumbersFlux for the first 3 numbers emitted that's greater than 5");
        ReactiveSources.intNumbersFlux()
                .filter(element -> element > 5)
                .map(element -> element * 10)
                .take(3)
                .subscribe(System.out::println);

        // Print each value from intNumbersFlux that's greater than 20. Print -1 if no elements are found
        ReactiveSources.intNumbersFlux()
                .filter(element -> element > 20)
                .defaultIfEmpty(-1)
                .subscribe(System.out::println);

        // Switch ints from intNumbersFlux to the right user from userFlux
        // TODO: Write code here

        // Print only distinct numbers from intNumbersFluxWithRepeat
        // TODO: Write code here

        // Print from intNumbersFluxWithRepeat excluding immediately repeating numbers
        // TODO: Write code here

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFluxWithException", LINE_DELIMITER);
        // Print values from intNumbersFluxWithException and print a message when error happens
        // TODO: Write code here

        // Print values from intNumbersFluxWithException and continue on errors
        // TODO: Write code here

        // Print values from intNumbersFluxWithException and when errors
        // happen, replace with a fallback sequence of -1 and -2
        // TODO: Write code here

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFlux", LINE_DELIMITER);
        // Print size of intNumbersFlux after the last item returns
        // TODO: Write code here

        // Collect all items of intNumbersFlux into a single list and print it
        // TODO: Write code here

        // Transform to a sequence of sums of adjacent two numbers
        // TODO: Write code here

        System.out.println("Press a key to end");
        System.in.read();
    }
}

class MyCustomSubscriber<T> extends BaseSubscriber<T> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("hookOnSubscribe");
        request(1);
    }

    @Override
    protected void hookOnNext(T value) {
        System.out.println(value.toString() + " received");
        request(1);
    }

    @Override
    protected void hookOnError(Throwable throwable) {
        System.out.println("hookOnError:" + throwable.getMessage());
    }
}