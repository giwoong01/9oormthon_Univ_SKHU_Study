package christmas.model;

import christmas.constant.menu.MenuConstant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {
    @Test
    void 유효한_메뉴_이름으로_객체_생성() {
        // Given
        String menuName = MenuConstant.CHAMPAGNE.getMenuName();
        int size = 2;

        // When
        Menu menu = new Menu(menuName, size);

        // Then
        assertThat(menu).isNotNull();
        assertThat(menu.getSize()).isEqualTo(size);
    }

    @Test
    void 유효하지_않은_메뉴_이름으로_객체_생성() {
        // Given
        String invalidMenuName = "밥";
        int size = 1;

        // When & Then
        assertThatThrownBy(() -> new Menu(invalidMenuName, size))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
