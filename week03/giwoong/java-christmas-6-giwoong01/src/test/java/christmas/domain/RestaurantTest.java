package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RestaurantTest {
    Restaurant restaurant;

    MenuAndCount mainMenu1;
    MenuAndCount mainMenu2;
    MenuAndCount mainMenu3;
    MenuAndCount dessertMenu1;
    MenuAndCount dessertMenu2;

    @BeforeEach
    public void setUp() {
        restaurant = new Restaurant();

        mainMenu1 = new MenuAndCount("티본스테이크", "1");
        mainMenu2 = new MenuAndCount("바비큐립", "1");
        mainMenu3 = new MenuAndCount("해산물파스타", "1");
        dessertMenu1 = new MenuAndCount("초코케이크", "1");
        dessertMenu2 = new MenuAndCount("아이스크림", "2");
    }

    @DisplayName("증정 메뉴를 받을 수 있다.")
    @ParameterizedTest
    @ValueSource(ints = {120000, 150000})
    void 증정_메뉴_성공(int value) {
        assertThat(restaurant.isPresentation(value)).isTrue();
    }

    @DisplayName("증정 메뉴를 받을 수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {119000, 50000})
    void 증정_메뉴_실패(int value) {
        assertThat(restaurant.isPresentation(value)).isFalse();
    }

    @DisplayName("크리스마스 디데이 할인이 적용된다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 12, 15, 23, 24, 25})
    void 크리스마스_디데이_할인_적용_성공(int value) {
        assertThat(restaurant.isChristmasDiscount(value)).isTrue();
    }

    @DisplayName("크리스마스 디데이 할인이 적용되지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {26, 28, 29, 30})
    void 크리스마스_디데이_할인_적용_실패(int value) {
        assertThat(restaurant.isChristmasDiscount(value)).isFalse();
    }

    @DisplayName("입력받은 날짜는 평일이다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7})
    void 날짜_평일(int value) {
        assertThat(restaurant.isWeekday(value)).isTrue();
    }

    @DisplayName("입력받은 날짜는 주말이다.")
    @ParameterizedTest
    @ValueSource(ints = {8, 9})
    void 날짜_주말(int value) {
        assertThat(restaurant.isWeekend(value)).isTrue();
    }

    @DisplayName("특별 할인 적용된다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    void 특별_할인_적용_성공(int value) {
        assertThat(restaurant.isStar(value)).isTrue();
    }

    @DisplayName("특별 할인 적용되지 않는다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 15, 20, 26})
    void 특별_할인_적용_실패(int value) {
        assertThat(restaurant.isStar(value)).isFalse();
    }

    @DisplayName("크리스마스 디데이 할인은 1000원으로 시작하여 크리스마스날이 다가올수록 날마다 할인 금액이 100원씩 증가.")
    @Test
    void 크리스마스_디데이_할인() {
        int date = 25;

        int discount = 3400;

        assertThat(restaurant.christmasDiscount(date)).isEqualTo(discount);
    }

    @DisplayName("평일엔 디저트 메뉴를 메뉴 1개당 2023원을 할인받는다.")
    @Test
    void 평일_디저트_할인() {
        List<MenuAndCount> menuAndCount = Arrays.asList(mainMenu1, dessertMenu1, dessertMenu2);
        MenuAndCounts menuAndCounts = new MenuAndCounts(menuAndCount);
        Buyer buyer = new Buyer(menuAndCounts);

        assertThat(restaurant.weekdayDiscount(buyer)).isEqualTo(2023 * 3);
    }

    @DisplayName("주말엔 메인 메뉴를 메뉴 1개당 2023원을 할인받는다.")
    @Test
    void 주말_메인_할인() {
        List<MenuAndCount> menuAndCount = Arrays.asList(mainMenu1, mainMenu2, dessertMenu1, dessertMenu2);
        MenuAndCounts menuAndCounts = new MenuAndCounts(menuAndCount);
        Buyer buyer = new Buyer(menuAndCounts);

        assertThat(restaurant.weekendDiscount(buyer)).isEqualTo(2023 * 2);
    }

    @DisplayName("특별할인은 1000원을 할인한다.")
    @Test
    void 특별_할인_1000원_반환() {
        assertThat(restaurant.starDiscount()).isEqualTo(1000);
    }

    @DisplayName("증정 이벤트로인한 할인은 샴페인 1개 가격이다.")
    @Test
    void 증정_이벤트_할인_25000원_반환() {
        assertThat(restaurant.presentationDiscount()).isEqualTo(25000);
    }

    @DisplayName("총혜택 금액이 2만원 이상이면 산타")
    @Test
    void 총혜택_금액_2만원_이상_산타() {
        int totalDiscount = 25000;

        String badge = restaurant.eventBadge(totalDiscount);

        assertThat(badge).isEqualTo("산타");
    }

    @DisplayName("총혜택 금액이 1만원 이상이면 트리")
    @Test
    void 총혜택_금액_1만원_이상_트리() {
        int totalDiscount = 15000;

        String badge = restaurant.eventBadge(totalDiscount);

        assertThat(badge).isEqualTo("트리");
    }

    @DisplayName("총혜택 금액이 5천원 이상이면 별")
    @Test
    void 총혜택_금액_5천원_이상_별() {
        int totalDiscount = 5000;

        String badge = restaurant.eventBadge(totalDiscount);

        assertThat(badge).isEqualTo("별");
    }

}