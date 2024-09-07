package lotto.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoResult {
    private final Map<PrizeRank, Integer> rankCounts = new HashMap<>();
    private int totalPrize = 0;

    public LottoResult(List<Lotto> lottos, WinningNumbers winningNumbers) {
        calculateRankCounts(lottos, winningNumbers);
    }

    private void calculateRankCounts(List<Lotto> lottos, WinningNumbers winningNumbers) {
        for (Lotto lotto : lottos) {
            int matchCount = (int) lotto.getLottoNumbers().stream()
                    .filter(winningNumbers.getWinningNumbers()::contains)
                    .count();
            boolean bonusMatch = lotto.getLottoNumbers().contains(winningNumbers.getBonusNumber());

            PrizeRank rank = PrizeRank.of(matchCount, bonusMatch);
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
            totalPrize += rank.getPrize();
        }
    }

    public double getProfitRate(int purchaseAmount) {
        if (purchaseAmount == 0) {
            return 0;
        }

        return (double) totalPrize / purchaseAmount * 100;
    }

    public Map<PrizeRank, Integer> getRankCounts() {
        return rankCounts;
    }
}
