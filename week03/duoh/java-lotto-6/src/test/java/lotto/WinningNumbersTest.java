package lotto;

import lotto.model.WinningNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class WinningNumbersTest {
    @DisplayName("당첨 번호의 개수가 6개가 아니라면 예외가 발생한다.")
    @Test
    void createWinningNumbersByInvalidSize() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5), 6))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6, 7), 7))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void createWinningNumbersByDuplicatedNumbers() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 4, 5), 6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void createWinningNumbersByDuplicatedBonusNumber() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 6))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("당첨 번호와 보너스 번호의 범위가 1~ 45가 아니라면 예외가 발생한다.")
    @Test
    void createWinningNumbersByInvalidNumberRange() {
        assertThatThrownBy(() -> new WinningNumbers(List.of(0, 1, 2, 3, 4, 5), 6))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 46), 6))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 46))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new WinningNumbers(List.of(1, 2, 3, 4, 5, 6), 0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
