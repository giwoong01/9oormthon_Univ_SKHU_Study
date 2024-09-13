package lotto;

import lotto.model.PurchaseAmount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class PurchaseAmountTest {
    @DisplayName("로또 구입 금액이 1,000원 단위가 아니라면 예외가 발생한다.")
    @Test
    void createPurchaseAmountByInvalidAmount() {
        assertThatThrownBy(() -> new PurchaseAmount(1500))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 구입 금액이 음수라면 예외가 발생한다.")
    @Test
    void createPurchaseAmountByNegativeAmount() {
        assertThatThrownBy(() -> new PurchaseAmount(-1000))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
