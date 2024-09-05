package lotto.view;

import lotto.model.Lotto;
import lotto.model.PrizeRank;

import java.util.List;
import java.util.Map;

public class OutputView {

    public void printGeneratedLottoNumbers(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getLottoNumbers());
        }
    }

    public void printResults(Map<PrizeRank, Integer> rankCounts, double profitRate) {
        System.out.println("\n당첨 통계\n---");
        PrizeRank[] ranks = PrizeRank.values();

        for (int i = ranks.length - 1; i >= 0; i--) {
            printPrize(ranks[i], rankCounts.getOrDefault(ranks[i], 0));
        }
        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);
    }

    private void printPrize(PrizeRank rank, int count) {
        if (rank == PrizeRank.MISS) {
            return;
        }
        String prizeMessage = createPrizeMessage(rank);
        System.out.printf(prizeMessage, rank.getMatchCount(), String.format("%,d", rank.getPrize()), count);
    }

    private String createPrizeMessage(PrizeRank rank) {
        if (rank == PrizeRank.SECOND_PRIZE) {
            return "%d개 일치, 보너스 볼 일치 (%s원) - %d개\n";
        }
        return "%d개 일치 (%s원) - %d개\n";
    }
}
