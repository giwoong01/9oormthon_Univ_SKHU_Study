package model;

import lotto.model.LottoAmount;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class LottoAmountTest {

    @DisplayName("구매가격의 입력값이 올바른지 검사한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1000, 1100})
    void validateLottos(int price) {
        Assertions.assertThatThrownBy(() -> new LottoAmount(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또의 가격을 입력받아 로또의 개수를 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {1000, 2000, 3000})
    void calculateLottoCount(int price) {
        LottoAmount lottoAmount = new LottoAmount(price);
        Assertions.assertThat(lottoAmount.calculateLottoCount()).isEqualTo(price / 1000);
    }
}
