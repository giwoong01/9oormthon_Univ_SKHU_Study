package model;

import lotto.model.Lotto;
import lotto.model.LottoResultManager;
import lotto.model.Rank;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LottoResultManagerTest {

    private LottoResultManager lrm;

    @BeforeEach
    void setUp() {
        this.lrm = new LottoResultManager();
    }

    @DisplayName("로또 번호가 당첨번호를 포함하는지 확인하고 등수를 반환한다.")
    @Test
    void calculateRank() {
        Assertions.assertThat(lrm.getLottoRankNumber(new Lotto(List.of(1, 2, 3, 7, 8, 9)), new Lotto(List.of(1, 2, 3, 4, 5, 6)))).isEqualTo(3);
    }

    @DisplayName("로또 번호를 전체적으로 검사하여 Rank 타입의 객체를 반환한다.")
    @Test
    void getRank() {
        Assertions.assertThat(lrm.getLottoRank(new Lotto(List.of(1, 2, 3, 4, 5, 7)), new Lotto(List.of(1, 2, 3, 4, 5, 6)), 7)).isEqualTo(Rank.THIRD);
    }

    @DisplayName("수익률을 계산한다.")
    @Test
    void calculateEarningRate() {
        Assertions.assertThat(lrm.calculateEarningRate(10000, 2000)).isEqualTo(20.0);
    }
}
