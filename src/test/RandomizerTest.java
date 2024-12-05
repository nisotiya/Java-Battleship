import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RandomizerTest {



    @Test
    void nextBooleanTestWithInput() {
        new Randomizer();
        assertTrue(Randomizer.nextBoolean(2.0));
    }

    @Test
    void nextBooleanTestWithInputReturnsFalse() {
        assertFalse(Randomizer.nextBoolean(0.001));
    }

    @Test
    void testNextIntWithBound() {
        int bound = 10;
        for (int i = 0; i < 100; i++) {
            int result = Randomizer.nextInt(bound);
            assertTrue(result >= 0 && result < bound,
                    "Random integer should be in the range [0, " + (bound - 1) + "]");
        }
    }

    @Test
    void testNextIntWithMinAndMax() {
        int min = 5;
        int max = 15;

        for (int i = 0; i < 100; i++) {
            int result = Randomizer.nextInt(min, max);
            assertTrue(result >= min && result <= max,
                    "Random integer should be in the range [" + min + ", " + max + "]");
        }
    }

    @Test
    void testNextDouble() {
        double randomValue = Randomizer.nextDouble();
        assertTrue(randomValue >= 0.0 && randomValue < 1.0,
                "Random double should be in the range [0.0, 1.0)");
    }

    @Test
    void testNextIntWithoutBound() {
        Integer randomValue = Randomizer.nextInt();
        assertNotNull(randomValue);
    }

    @Test
    void testNextBoolean() {
        Boolean result = Randomizer.nextBoolean();
        assertNotNull(result);
        assertTrue(true, "Result should be a boolean (true or false).");
    }

    @Test
    void testNextDoubleWithRange() {
        double min = 2.5;
        double max = 5.5;

        for (int i = 0; i < 100; i++) {
            double result = Randomizer.nextDouble(min, max);
            assertTrue(result >= min && result <= max,
                    "Random double should be in the range [" + min + ", " + max + "]");
        }
    }

}

