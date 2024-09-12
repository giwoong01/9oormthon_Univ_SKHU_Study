package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuAndCountTest {
    @DisplayName("메뉴의 개수가 숫자가 아니면 예외가 발생한다.")
    @Test
    void 메뉴_개수_숫자_실패() {
        assertThatThrownBy(() -> new MenuAndCount("티본스테이크", "a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("메뉴의 개수가 숫자이면 예외가 발생하지 않는다.")
    @Test
    void 메뉴_개수_숫자_성공() {
        assertThatCode(() -> new MenuAndCount("바비큐립", "1"))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴의 개수가 1미만이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-5"})
    void 메뉴_개수_1미만_실패(String value) {
        assertThatThrownBy(() -> new MenuAndCount("티본스테이크", value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("메뉴의 개수가 1이상이면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "5", "10"})
    void 메뉴_개수_1이상_성공(String value) {
        assertThatCode(() -> new MenuAndCount("바비큐립", value))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴판에 없는 메뉴를 입력하면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"스테이크", "토마토파스타", "제로사이다"})
    void 메뉴판_이름_존재_실패(String value) {
        assertThatThrownBy(() -> new MenuAndCount(value, "1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("메뉴판에 있는 메뉴를 입력하면 예외가 발생하지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"양송이수프", "타파스", "시저샐러드",
            "티본스테이크", "바비큐립", "해산물파스타", "크리스마스파스타",
            "초코케이크", "아이스크림",
            "제로콜라", "레드와인", "샴페인"})
    void 메뉴판_이름_존재_성공(String value) {
        assertThatCode(() -> new MenuAndCount(value, "1"))
                .doesNotThrowAnyException();
    }

}