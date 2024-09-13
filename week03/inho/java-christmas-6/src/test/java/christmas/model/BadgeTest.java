package christmas.model;

import christmas.constant.event.BadgeName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BadgeTest {
    @Test
    void 값이_5000_미만일_경우_NO_BADGE를_반환() {
        // Given
        int eventCost = 4000;

        // When
        Badge badge = new Badge(eventCost);

        // Then
        assertThat(badge.getBadgeName()).isEqualTo(BadgeName.NO_BADGE.badgeName());
    }

    @Test
    void 값이_5000_이상_10000_미만일_경우_NO_BADGE를_반환() {
        // Given
        int eventCost = 7000;

        // When
        Badge badge = new Badge(eventCost);

        // Then
        assertThat(badge.getBadgeName()).isEqualTo(BadgeName.LEVEL_1.badgeName());
    }

    @DisplayName("eventCost가 10000 이상 20000 미만일 경우 LEVEL_2 배지를 반환해야 한다.")
    @Test
    void 값이_10000_이상_20000_미만일_경우_NO_BADGE를_반환() {
        // Given
        int eventCost = 15000;

        // When
        Badge badge = new Badge(eventCost);

        // Then
        assertThat(badge.getBadgeName()).isEqualTo(BadgeName.LEVEL_2.badgeName());
    }

    @DisplayName("eventCost가 20000 이상일 경우 LEVEL_3 배지를 반환해야 한다.")
    @Test
    void 값이_20000_이상일_경우_NO_BADGE를_반환() {
        // Given
        int eventCost = 25000;

        // When
        Badge badge = new Badge(eventCost);

        // Then
        assertThat(badge.getBadgeName()).isEqualTo(BadgeName.LEVEL_3.badgeName());
    }
}
