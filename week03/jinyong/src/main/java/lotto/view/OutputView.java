package lotto.view;

public class OutputView {
    private static final String PURCHASED_LOTTOS = "개를 구매했습니다.";
    private static final String WINNING_STATISTICS = "당첨 통계\n" +
            "---";

    public static void printLottoCount(int count) {
        System.out.println("\n" + count + PURCHASED_LOTTOS);
    }

    public static void printWinningStatisticsTwoLines() {
        System.out.println(WINNING_STATISTICS);
    }

    public static void printWinningStatistics(String message, int count) {
        System.out.println(message + " - " + count + "개");
   }

    public static void printEarningRate(double earningRate) {
        System.out.println("총 수익률은 " + String.format("%.1f%%", earningRate * 100) + "입니다.");
    }
}
