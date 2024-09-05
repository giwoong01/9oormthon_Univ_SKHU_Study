package lotto.model;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

import static lotto.model.Lotto.*;
import static lotto.model.PurchaseAmount.LOTTO_PRICE;

public class LottoGenerator {
    public static List<Lotto> generate(PurchaseAmount purchaseAmount) {
        int lottoCount = purchaseAmount.getPurchaseAmount() / LOTTO_PRICE;
        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < lottoCount; i++) {
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER, LOTTO_NUMBER_COUNT);

            try {
                Lotto lotto = new Lotto(numbers);
                lottos.add(lotto);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                i--;
            }
        }

        return lottos;
    }
}
