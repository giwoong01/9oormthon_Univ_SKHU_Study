package lotto.model;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public Map<Rank,Integer> setResultMap(){
        Map<Rank,Integer> returningMap = new LinkedHashMap<>();
        for (Rank rank : Rank.values()) {
            returningMap.put(rank, 0);
        }
        return returningMap;
    }
}
