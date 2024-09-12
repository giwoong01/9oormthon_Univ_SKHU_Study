package christmas.model.order;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.model.menu.Menu;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OrdersTest {

    @Test
    @DisplayName("주문 목록의 총 가격을 올바르게 계산할 수 있다.")
    public void testGetTotalPrice() {
        // given
        Order order1 = new Order(Menu.MUSHROOM_SOUP, 2);  // 2 * 6000
        Order order2 = new Order(Menu.T_BONE_STEAK, 1);   // 1 * 55000
        Order order3 = new Order(Menu.ICE_CREAM, 3);      // 3 * 5000 =
        Orders orders = new Orders(List.of(order1, order2, order3));

        // when
        int totalPrice = orders.getTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(2 * 6000 + 55000 + 3 * 5000);
    }
}
