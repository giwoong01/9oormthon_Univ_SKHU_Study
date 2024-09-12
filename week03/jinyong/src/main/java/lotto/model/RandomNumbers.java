package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RandomNumbers {

    private static final int FIRST_NUMBER = 1;
    private static final int LAST_NUMBER = 45;
    private static final int COUNT_OF_NUMBERS = 6;

    public static List<Integer> generateRandomNumbers() {
        List<Integer> randomNumbers = new ArrayList<>(Randoms.pickUniqueNumbersInRange(FIRST_NUMBER, LAST_NUMBER, COUNT_OF_NUMBERS));
        randomNumbers.sort(Comparator.comparingInt(o -> o));
        return randomNumbers;
    }
}
