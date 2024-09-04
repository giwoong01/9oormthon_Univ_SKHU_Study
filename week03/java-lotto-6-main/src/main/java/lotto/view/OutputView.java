package lotto.view;

import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;

public class OutputView {


    public void printLottoNumbers(List<Lotto> lottoList) {
        System.out.println(lottoList.size() + "개를 구매했습니다.");
        for(Lotto lotto : lottoList){
            System.out.println(lotto.getNumbers());
        }
    }

    public void printResults(Map<String, Integer> results, double earningsRate) {
        System.out.println("당첨 통계");
        System.out.println("---");
        System.out.println("3개 일치 (5,000원) - " + results.get("5등") + "개");
        System.out.println("4개 일치 (50,000원) - " + results.get("4등") + "개");
        System.out.println("5개 일치 (1,500,000원) - " + results.get("3등") + "개");
        System.out.println("5개 일치, 보너스 볼 일치 (30,000,000원) - " + results.get("2등") + "개");
        System.out.println("6개 일치 (2,000,000,000원) - " + results.get("1등") + "개");
        System.out.printf("총 수익률은 %.1f%%입니다.%n", earningsRate);
    }

}
