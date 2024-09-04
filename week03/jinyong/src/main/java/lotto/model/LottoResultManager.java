package lotto.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class LottoResultManager {

    public int getLottoRankNumber(Lotto winningLotto, Lotto userLotto) {
        return (int) userLotto.getNumbers().stream()
                .filter(winningLotto::containsWinningNumber)
                .count();
    }

    public Rank getLottoRank(Lotto winningLotto, Lotto userLotto, int bonusNumber) {
        int count = getLottoRankNumber(winningLotto, userLotto);
        boolean matchBonus = userLotto.containsWinningNumber(bonusNumber);
        return Rank.provideRank(count, matchBonus);
    }

    public double calculateEarningRate(int totalPurchaseAmount, int totalWinningAmount) {
        double profitRate = (double) totalWinningAmount / totalPurchaseAmount * 100;
        profitRate = Math.round(profitRate * 100.0) / 100.0;
        return profitRate;
    }


}
