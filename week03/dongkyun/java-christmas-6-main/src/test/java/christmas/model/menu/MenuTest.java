package christmas.model.menu;

import christmas.message.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    @DisplayName("Menu enum의 from 메서드가 올바른 Menu를 반환하는지 확인한다.")
    public void testFromValidMenu() {
        Assertions.assertThat(Menu.from("양송이수프")).isEqualTo(Menu.MUSHROOM_SOUP);
        Assertions.assertThat(Menu.from("레드와인")).isEqualTo(Menu.RED_WINE);
    }

    @Test
    @DisplayName("Menu enum의 from 메서드가 유효하지 않은 메뉴 이름에 대해 예외를 던지는지 확인한다.")
    public void testFromInvalidMenu() {
        Assertions.assertThatThrownBy(() -> Menu.from("존재하지 않는 메뉴"))
                .hasMessage(ErrorMessage.INVALID_ORDER_ERROR.getMessage());
    }
}
