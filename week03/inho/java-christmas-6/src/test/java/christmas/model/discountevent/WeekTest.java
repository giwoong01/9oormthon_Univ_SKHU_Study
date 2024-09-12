package christmas.model.discountevent;

import christmas.constant.menu.MenuConstant;
import christmas.model.Menu;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeekTest {

    @Test
    void 주말일_떄_메인_메뉴_할인_적용() {
        // Given
        int date = 15;
        List<Menu> menus = Arrays.asList(
                new Menu(MenuConstant.T_BONE_STEAK.getMenuName(), 1),
                new Menu(MenuConstant.CHOCOLATE_CAKE.getMenuName(), 2)
        );

        // When
        Week weekEvent = new Week(date, menus);

        // Then
        int expectedDiscount = -1 * 2023;
        assertEquals(expectedDiscount, weekEvent.getDiscountPrice());
    }

    @Test
    void 평일일_떄_디저트_메뉴_할인_적용() {
        // Given
        int date = 12;
        List<Menu> menus = Arrays.asList(
                new Menu(MenuConstant.T_BONE_STEAK.getMenuName(), 1),
                new Menu(MenuConstant.CHOCOLATE_CAKE.getMenuName(), 2)
        );

        // When
        Week weekEvent = new Week(date, menus);

        // Then
        int expectedDiscount = -2 * 2023;
        assertEquals(expectedDiscount, weekEvent.getDiscountPrice());
    }
}
