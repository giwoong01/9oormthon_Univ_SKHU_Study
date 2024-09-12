package christmas.model.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.model.menu.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    @DisplayName("Order 객체가 올바르게 생성할 수 있다.")
    public void testOrderCreation() {
        // given
        Menu menu = Menu.MUSHROOM_SOUP;
        int count = 3;

        // when
        Order order = new Order(menu, count);

        // then
        assertThat(order.menu()).isEqualTo(menu);
        assertThat(order.count()).isEqualTo(count);
    }

    @Test
    @DisplayName("from 메서드로 Order 객체를 생성할 수 있다.")
    public void testFrom() {
        // given
        String inputMenu = "양송이수프";
        int inputCount = 2;

        // when
        Order order = Order.from(inputMenu, inputCount);

        // then
        assertThat(order.menu()).isEqualTo(Menu.MUSHROOM_SOUP);
        assertThat(order.count()).isEqualTo(inputCount);
    }

    @Test
    @DisplayName("유효하지 않은 메뉴 이름이 주어졌을 때 예외가 발생한다.")
    public void testInvalidMenu() {
        // given
        String invalidMenu = "존재하지 않는 메뉴";
        int count = 1;

        // when & then
        assertThatThrownBy(() -> Order.from(invalidMenu, count))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }
}
