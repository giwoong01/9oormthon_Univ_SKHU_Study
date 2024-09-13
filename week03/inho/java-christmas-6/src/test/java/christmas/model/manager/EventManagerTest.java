package christmas.model.manager;

import christmas.model.manager.EventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import christmas.model.Event;
import christmas.model.Menu;
import christmas.model.discountevent.DDay;
import christmas.model.discountevent.Gift;
import christmas.model.discountevent.SpecialDay;
import christmas.model.discountevent.Week;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EventManagerTest {

    private EventManager eventManager;

    @BeforeEach
    void setUp() {
        eventManager = new EventManager();
    }

    @Test
    void 모든_이벤트_적용_시에_4개의_이벤트_적용() {
        // given
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu("초코케이크", 10));
        int date = 25;
        int price = 120000;

        // when
        eventManager.settingEvent(date, menus, price);

        // then
        List<Event> events = eventManager.getEvents();
        assertThat(events).hasSize(4);
    }

    @Test
    void 아무런_이벤트_적용_안_할시_0개의_이벤트_적용() {
        // given
        List<Menu> menus = new ArrayList<>();
        int date = 26;
        int price = 100000;

        // when
        eventManager.settingEvent(date, menus, price);

        // then
        List<Event> events = eventManager.getEvents();
        assertThat(events).hasSize(0);
    }
}
