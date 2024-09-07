package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.List;

public class LottoGenerator {
    public static List<Integer> generateWinningNumber() {
        List<Integer> randomNumbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        randomNumbers.sort(Integer::compareTo);
        return randomNumbers;
    }
}

