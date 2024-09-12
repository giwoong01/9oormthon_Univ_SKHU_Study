package lotto;

import lotto.model.Lotto;
import lotto.model.LottoResult;
import lotto.model.PrizeRank;
import lotto.model.WinningNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoResultTest {
    @DisplayName("로또 결과가 올바르게 집계되는지 테스트")
    @Test
    void calculateLottoResult() {
        LottoResult result = new LottoResult(
                List.of(
                        new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                        new Lotto(List.of(1, 2, 3, 4, 5, 8)),
                        new Lotto(List.of(1, 2, 3, 9, 10, 11)),
                        new Lotto(List.of(10, 11, 12, 13, 14, 15))
                ),
                new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7)
        );

        assertThat(result.getRankCounts().getOrDefault(PrizeRank.FIRST_PRIZE, 0)).isEqualTo(0);
        assertThat(result.getRankCounts().getOrDefault(PrizeRank.SECOND_PRIZE, 0)).isEqualTo(1);
        assertThat(result.getRankCounts().getOrDefault(PrizeRank.THIRD_PRIZE, 0)).isEqualTo(1);
        assertThat(result.getRankCounts().getOrDefault(PrizeRank.FOURTH_PRIZE, 0)).isEqualTo(0);
        assertThat(result.getRankCounts().getOrDefault(PrizeRank.FIFTH_PRIZE, 0)).isEqualTo(1);
        assertThat(result.getRankCounts().getOrDefault(PrizeRank.MISS, 0)).isEqualTo(1);
    }

    @DisplayName("수익률이 올바르게 계산되는지 테스트")
    @Test
    void calculateProfitRate() {
        LottoResult result = new LottoResult(
                List.of(
                        new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                        new Lotto(List.of(1, 2, 3, 4, 5, 8)),
                        new Lotto(List.of(1, 2, 3, 9, 10, 11)),
                        new Lotto(List.of(10, 11, 12, 13, 14, 15))
                ),
                new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 7)
        );

        double profitRate = result.getProfitRate(4000);
        double expectedProfitRate = (double) (30_000_000 + 1_500_000 + 5_000) / 4000 * 100;

        assertThat(profitRate).isEqualTo(expectedProfitRate);
    }
}
