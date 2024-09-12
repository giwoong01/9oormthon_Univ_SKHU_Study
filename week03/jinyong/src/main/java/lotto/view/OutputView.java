package lotto.view;

import lotto.model.Lotto;
import lotto.model.Lottos;

public class OutputView {

    private static final String PURCHASED_LOTTOS = "\n%d개를 구매했습니다.\n";
    private static final String WINNING_STATISTICS = "\n당첨 통계\n---";
    private static final String EARNING_RATE = "총 수익률은 %s입니다.";

    public static void printWinningStatisticsTwoLines() {
        System.out.println(WINNING_STATISTICS);
    }

    public static void printWinningStatistics(String message, int count) {
        System.out.printf("%s - %d개\n", message, count);
   }

    public static void printEarningRate(double earningRate) {
        System.out.printf(EARNING_RATE, String.format("%.1f%%", earningRate));
    }

    public static void printLottos(Lottos userLottos) {
        System.out.printf(PURCHASED_LOTTOS, userLottos.size());
        userLottos.lottos().stream()
                .map(Lotto::getNumbers)
                .forEach(System.out::println);
    }
}
