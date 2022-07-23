package by.dma.reactivegames.sources;

import java.util.stream.Stream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author dzmitry.marudau
 * @since 2022.07
 */
public final class StreamSources {

    private StreamSources() {
    }

    public static Stream<String> stringNumbers() {
        return Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
    }

    public static Stream<Integer> intNumbers() {
        return Stream.iterate(0, i -> i + 2)
                .limit(10);
    }

    public static Stream<User> users() {
        return Stream.of(
                new User(1, "Lionel", "Messi"),
                new User(2, "Cristiano", "Ronaldo"),
                new User(2, "Diego", "Maradona"),
                new User(4, "Zinedine", "Zidane"),
                new User(5, "Jürgen", "Klinsmann"),
                new User(6, "Gareth", "Bale")
        );
    }
}
