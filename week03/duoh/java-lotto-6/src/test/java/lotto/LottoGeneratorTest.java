package lotto;

import lotto.model.Lotto;
import lotto.model.LottoGenerator;
import lotto.model.PurchaseAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoGeneratorTest {
    @DisplayName("로또 구입 금액에 맞는 로또 개수가 발행되는지 테스트")
    @Test
    void generateLottoByValidPurchaseAmount() {
        PurchaseAmount purchaseAmount = new PurchaseAmount(8000);
        List<Lotto> lottos = LottoGenerator.generate(purchaseAmount);

        assertThat(lottos).hasSize(8);
    }
}
