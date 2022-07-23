package by.dma.reactivegames.exercises;

/**
 * Playing with StreamSources.
 *
 * @author dzmitry.marudau
 * @since 2022.07
 */
public class StreamSourceOperations {

    private static final String LINE_DELIMITER = "#".repeat(20);

    public static void main(String... args) {
        // Use StreamSources.intNumbers() and StreamSources.users()
        System.out.printf("%s %s %s%n", LINE_DELIMITER, "intNumbers & users()", LINE_DELIMITER);
        // Print all numbers in the intNumbers stream
        // TODO: Write code here

        // Print numbers from intNumbers that are less than 5
        // TODO: Write code here

        // Print the second and third numbers in intNumbers that's greater than 5
        // TODO: Write code here

        //  Print the first number in intNumbers that's greater than 5.
        //  If nothing is found, print -1
        // TODO: Write code here

        // Print first names of all users in userStream
        // TODO: Write code here

        // Print first names in userStream for users that have IDs from number stream
        // TODO: Write code here

    }
}
