package org.example.utilities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals; // assertTrue, assertEquals, assertNotNull

public class ConsoleTest {
    private final Console console = new Console();

    @Test
    @DisplayName("Test that Console Colorizer Utility is correctly consumed by Log4j")
    public void testAllConsoleColors() {
        System.out.println("console object instance:" + console);
        console.log(Console.Color.CYAN, "This is log output from console.log!");

        final String[] expected = {
            "\033[0;31m",
            "\033[0;33m",
            "\033[0;32m",
            "\033[0;34m",
            "\033[0;35m",
        };
        final String[] actual = {
            Console.Color.RED,
            Console.Color.YELLOW,
            Console.Color.GREEN,
            Console.Color.BLUE,
            Console.Color.PURPLE,
        };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
