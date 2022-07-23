package by.dma.reactivegames.exercises;

import java.io.IOException;

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
        // TODO: Write code here

        // Print all users in the `userFlux` stream
        // TODO: Write code here

        // Get all numbers in the `intNumbersFlux` stream
        // into a List and print the list and its size
        // TODO: Write code here

        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumberMono & userMono", LINE_DELIMITER);
        // Print the value from `intNumberMono` when it emits
        // TODO: Write code here

        // Get the value from the Mono(`intNumberMono`) into an integer variable
        // TODO: Write code here

        // Subscribe to a flux using the error and completion hooks
        // TODO: Write code here

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
