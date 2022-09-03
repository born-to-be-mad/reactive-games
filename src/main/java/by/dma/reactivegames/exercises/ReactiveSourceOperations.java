package by.dma.reactivegames.exercises;

import java.io.IOException;
import java.util.List;

import by.dma.reactivegames.sources.ReactiveSources;
import by.dma.reactivegames.sources.User;
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
        List<Integer> integerList = ReactiveSources.intNumbersFlux().toStream().toList();
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
                        error -> System.out.printf("intNumbersFluxWithException error: %s%n", error),
                        () -> System.out.println("intNumbersFluxWithException completed")
                );

        // Subscribe to a flux using an implementation of BaseSubscriber
        // TODO: Write code here

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "unresponsiveFlux & unresponsiveMono", LINE_DELIMITER);
        // Get the value from the Mono into a String variable but give up after 5 seconds
        // TODO: Write code here

        // Get the value from unresponsiveFlux into a String list but give up after 5 seconds
        // Come back and do this when you've learnt about operators!
        // TODO: Write code here

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbersFlux & intNumbersFluxWithRepeat", LINE_DELIMITER);

        // Print all values from intNumbersFlux that's greater than 5
        // TODO: Write code here

        // Print 10 times each value from intNumbersFlux that's greater than 5
        // TODO: Write code here

        // Print 10 times each value from intNumbersFlux for the first 3 numbers emitted that's greater than 5
        // TODO: Write code here

        // Print each value from intNumbersFlux that's greater than 20. Print -1 if no elements are found
        // TODO: Write code here

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
