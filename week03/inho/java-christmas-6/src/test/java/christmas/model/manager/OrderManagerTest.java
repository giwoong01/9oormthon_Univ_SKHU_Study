package christmas.model.manager;

import christmas.constant.menu.MenuConstant;
import christmas.model.Event;
import christmas.model.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderManagerTest {

    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        orderManager = new OrderManager();
    }

    @Test
    void 메뉴가_주어질_경우_총합을_계산() {
        // given
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu("티본스테이크", 1));
        menus.add(new Menu("아이스크림", 1));

        // when
        int totalPrice = orderManager.getBeforeDiscountTotalPrice(menus);

        // then
        assertThat(totalPrice).isEqualTo(60000);
    }

    @Test
    void 이벤트가_주어질_경우_할인금액을_계산() {
        // given
        List<Event> events = new ArrayList<>();
        Event event1 = mock(Event.class);
        when(event1.getDiscountPrice()).thenReturn(5000);

        Event event2 = mock(Event.class);
        when(event2.getDiscountPrice()).thenReturn(10000);

        events.add(event1);
        events.add(event2);

        // when
        int totalBenefit = orderManager.getBenefitTotalPrice(events);

        // then
        assertThat(totalBenefit).isEqualTo(15000);
    }

    @Test
    void 선물이_있을_경우_최종가격을_계산() {
        // given
        int beforeDiscountTotalPrice = 30000;
        int benefitPrice = 15000;

        // when
        int finalPrice = orderManager.getAfterDiscountTotalPrice(true, beforeDiscountTotalPrice, benefitPrice);

        // then
        assertThat(finalPrice).isEqualTo(45000 + MenuConstant.CHAMPAGNE.getManuPrice());
    }

    @Test
    void 선물이_없을_경우_최종가격을_계산() {
        // given
        int beforeDiscountTotalPrice = 30000;
        int benefitPrice = 15000;

        // when
        int finalPrice = orderManager.getAfterDiscountTotalPrice(false, beforeDiscountTotalPrice, benefitPrice);

        // then
        assertThat(finalPrice).isEqualTo(45000);
    }
}
