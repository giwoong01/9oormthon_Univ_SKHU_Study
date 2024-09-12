package model;

import lotto.model.Lottos;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LottosTest {

    @DisplayName("구입한 금액에서 비롯된 갯수만큼 로또를 생성한다.")
    @Test
    void generateLottos() {
        // given
        int count = 5;

        // when
        Lottos lottos = Lottos.generateLottos(count);

        // then
        assertThat(lottos.size()).isEqualTo(count);
    }
}
