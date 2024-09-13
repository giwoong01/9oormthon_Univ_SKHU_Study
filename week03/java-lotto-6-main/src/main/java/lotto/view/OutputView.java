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

        System.out.println("3개 일치 (" + format(Prize.FIFTH.getPrizeMoney()) + "원) - " + results.get(Prize.FIFTH) + "개");
        System.out.println("4개 일치 (" + format(Prize.FOURTH.getPrizeMoney()) + "원) - " + results.get(Prize.FOURTH) + "개");
        System.out.println("5개 일치 (" + format(Prize.THIRD.getPrizeMoney()) + "원) - " + results.get(Prize.THIRD) + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (" + format(Prize.SECOND.getPrizeMoney()) + "원) - " + results.get(Prize.SECOND) + "개");
        System.out.println("6개 일치 (" + format(Prize.FIRST.getPrizeMoney()) + "원) - " + results.get(Prize.FIRST) + "개");

        System.out.printf("총 수익률은 %.1f%%입니다.%n", earningsRate);
    }

    private String format(int amount) {
        return String.format("%,d", amount);
    }
}
