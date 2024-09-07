package lotto.view;

import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;
import lotto.domain.Prize;

public class OutputView {

    public void printLottoNumbers(List<Lotto> lottoList) {
        System.out.println(lottoList.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottoList) {
            System.out.println(lotto.getNumbers());
        }
    }

    public void printResults(Map<Prize, Integer> results, double earningsRate) {
        System.out.println("당첨 통계");
        System.out.println("---");
        for (Prize prize : Prize.values()) {
            if (prize != Prize.NONE) {
                System.out.println(prize.getDescription() + " (" + prize.getPrizeMoney() + "원) - " + results.get(prize) + "개");
            }
        }
        System.out.printf("총 수익률은 %.1f%%입니다.%n", earningsRate);
    }
}
