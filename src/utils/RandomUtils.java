package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUtils {

    private static final Random random = new Random();

    public static List<Integer> getRandomNumbers(int quant, int rangeMax) {
        List<Integer> randomNumbers = new ArrayList<>();

        for (int i = 0; i < quant; i++) {
            int randomNum = random.nextInt(rangeMax);
            while (randomNumbers.contains(randomNum)) {
                randomNum = random.nextInt(rangeMax);
            }

            randomNumbers.add(randomNum);
        }

        Collections.sort(randomNumbers);
        return randomNumbers;
    }
}
