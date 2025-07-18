package utils;

import java.util.Random;

public class RandomUtils {
    private static Random random = new Random();

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public static boolean coinFlip() {
        return random.nextBoolean();
    }
}

