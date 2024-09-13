package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuAndCountsTest {
    MenuAndCount mainMenu1;
    MenuAndCount mainMenu2;
    MenuAndCount mainMenu3;
    MenuAndCount drinkMenu1;
    MenuAndCount drinkMenu2;

    @BeforeEach
    public void setUp() {
        mainMenu1 = new MenuAndCount("티본스테이크", "1");
        mainMenu2 = new MenuAndCount("티본스테이크", "15");
        mainMenu3 = new MenuAndCount("바비큐립", "1");
        drinkMenu1 = new MenuAndCount("제로콜라", "5");
        drinkMenu2 = new MenuAndCount("레드와인", "1");
    }

    @DisplayName("중복된 메뉴가 있으면 예외가 발생한다.")
    @Test
    void 중복_메뉴_실패() {
        List<MenuAndCount> menuAndCounts = Arrays.asList(mainMenu1, mainMenu2, mainMenu3);

        assertThatThrownBy(() -> new MenuAndCounts(menuAndCounts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @DisplayName("중복된 메뉴가 없으면 예외가 발생하지 않는다.")
    @Test
    void 중복_메뉴_성공() {
        List<MenuAndCount> menuAndCounts = Arrays.asList(mainMenu1, mainMenu3, drinkMenu1);

        assertThatCode(() -> new MenuAndCounts(menuAndCounts))
                .doesNotThrowAnyException();
    }


    @DisplayName("음료만 주문할 시 예외가 발생한다.")
    @Test
    void 음료_주문_실패() {
        List<MenuAndCount> menuAndCounts = Arrays.asList(drinkMenu1, drinkMenu2);

        assertThatThrownBy(() -> new MenuAndCounts(menuAndCounts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 음료만 주문할 수 없습니다. 다시 입력해 주세요.");
    }

    @DisplayName("음료가 다른 메뉴와 함께 주문할 시 예외가 발생하지 않는다.")
    @Test
    void 음료_주문_성공() {
        List<MenuAndCount> menuAndCounts = Arrays.asList(mainMenu1, drinkMenu1, drinkMenu2);

        assertThatCode(() -> new MenuAndCounts(menuAndCounts))
                .doesNotThrowAnyException();
    }

    @DisplayName("주문한 메뉴가 20개 초과하면 예외가 발생한다.")
    @Test
    void 주문_메뉴_20개_초과_실패() {
        List<MenuAndCount> menuAndCounts = Arrays.asList(mainMenu2, drinkMenu1, drinkMenu2);

        assertThatThrownBy(() -> new MenuAndCounts(menuAndCounts))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 메뉴는 한 번에 20개까지만 주문 가능합니다. 다시 입력해 주세요");
    }

    @DisplayName("주문한 메뉴가 20개 이하이면 예외가 발생하지 않는다.")
    @Test
    void 주문_메뉴_20개_이하_성공() {
        List<MenuAndCount> menuAndCounts = Arrays.asList(mainMenu1, drinkMenu1, drinkMenu2);

        assertThatCode(() -> new MenuAndCounts(menuAndCounts))
                .doesNotThrowAnyException();
    }

    @DisplayName("할인 전 총주문 금액을 반환한다.")
    @Test
    void 할인_전_총주문_금액_반환() {
        List<MenuAndCount> menuAndCount = Arrays.asList(mainMenu1, mainMenu3, drinkMenu2);
        MenuAndCounts menuAndCounts = new MenuAndCounts(menuAndCount);

        int totalPrice = 55000 + 54000 + 60000;

        assertThat(menuAndCounts.calculateTotalPrice()).isEqualTo(totalPrice);
    }

}