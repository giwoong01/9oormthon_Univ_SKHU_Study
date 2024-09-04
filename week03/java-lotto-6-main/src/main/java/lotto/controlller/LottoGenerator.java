package lotto.controlller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LottoGenerator {
    public static List<Integer> generateWinningNumber() {
        List<Integer> randomNumbers = new ArrayList<>();
        Random random = new Random();
        while (randomNumbers.size() < 6) {
            int number = random.nextInt(45) + 1;
            if (!randomNumbers.contains(number)) {
                randomNumbers.add(number);
            }
        }
        return randomNumbers;
    }
}

