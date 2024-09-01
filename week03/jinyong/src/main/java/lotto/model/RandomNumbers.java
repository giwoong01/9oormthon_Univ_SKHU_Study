package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.Collections;
import java.util.List;

public class RandomNumbers {
    private static final int FIRST_NUMBER = 1;
    private static final int LAST_NUMBER = 45;
    private static final int COUNT_OF_NUMBERS = 6;
    public static List<Integer> generateRandomNumbers() {
        List<Integer> randomNumbers = Randoms.pickUniqueNumbersInRange(FIRST_NUMBER, LAST_NUMBER, COUNT_OF_NUMBERS);
        Collections.sort(randomNumbers);
        return randomNumbers;
    }
}
