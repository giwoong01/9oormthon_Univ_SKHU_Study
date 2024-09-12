package view;

import lotto.view.InputView;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InputViewTest {

    @DisplayName("로또 번호로 올바른 입력값을 받지 않으면 예외를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,a", "1,2,3,4,5,6!"})
    void inputLottoNumbers(String input) {
        Assertions.assertThatThrownBy(() -> InputView.stringToNumbers(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
