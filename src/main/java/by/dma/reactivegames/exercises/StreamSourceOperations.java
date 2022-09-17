package by.dma.reactivegames.exercises;

import by.dma.reactivegames.sources.StreamSources;
import by.dma.reactivegames.sources.User;

/**
 * Exercises to play with StreamSources.
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
        System.out.println("# Print all numbers in the intNumbers stream");
        StreamSources.intNumbers()
                .forEach(System.out::println);

        // Print numbers from intNumbers that are less than 5
        System.out.println("# Print numbers from intNumbers that are less than 5");
        StreamSources.intNumbers()
                .filter(num -> num < 5)
                .forEach(System.out::println);

        // Print the second and third numbers in intNumbers that's greater than 5
        System.out.println("# Print the second and third numbers in intNumbers that's greater than 5");
        StreamSources.intNumbers()
                .filter(num -> num > 5)
                .skip(1)
                .limit(2)
                .forEach(System.out::println);

        //  Print the first number in intNumbers that's greater than 5.
        //  If nothing is found, print -1
        System.out.println("# Print the first number in intNumbers that's greater than 5. If nothing is found, print -1");
        System.out.println(
                StreamSources.intNumbers()
                        .filter(num -> num > 5)
                        .findFirst()
                        .orElse(-1));

        // Print first names of all users in userStream
        System.out.println("# Print first names of all users in userStream");
        StreamSources.users()
                .map(User::firstName)
                .forEach(System.out::println);

        // Print first names in userStream for users that have IDs from number stream
        System.out.println("# Print first names in userStream for users that have IDs from number stream - variant 1");
        StreamSources.users()
                .filter(user -> StreamSources.intNumbers().anyMatch(num -> num == user.id()))
                .map(User::firstName)
                .forEach(System.out::println);

        System.out.println("# Print first names in userStream for users that have IDs from number stream - variant 2");
        StreamSources.intNumbers()
                .flatMap(id -> StreamSources.users()
                        .filter(user -> user.id() == id))
                .map(User::firstName)
                .forEach(System.out::println);
    }
}
