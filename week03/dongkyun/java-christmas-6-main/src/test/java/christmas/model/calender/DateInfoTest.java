package christmas.model.calender;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateInfoTest {

    @Test
    @DisplayName("DateInfo 객체가 올바르게 생성할 수 있다.")
    public void testDateInfoCreation() {
        // given
        int day = 15;

        // when
        DateInfo dateInfo = new DateInfo(day);

        // then
        Assertions.assertThat(dateInfo.getYear()).isEqualTo(2023);
        Assertions.assertThat(dateInfo.getMonth()).isEqualTo(12);
        Assertions.assertThat(dateInfo.getDay()).isEqualTo(day);
    }

    @Test
    @DisplayName("주어진 날짜가 주말인지 확인할 수 있다.")
    public void testIsWeekend() {
        // given
        DateInfo saturdayDate = new DateInfo(23);
        DateInfo fridayDate = new DateInfo(22);
        DateInfo mondayDate = new DateInfo(18);

        // when & then
        Assertions.assertThat(saturdayDate.isWeekend()).isTrue();
        Assertions.assertThat(fridayDate.isWeekend()).isTrue();
        Assertions.assertThat(mondayDate.isWeekend()).isFalse();
    }

    @Test
    @DisplayName("주어진 날짜가 일요일인지 확인할 수 있다.")
    public void testIsSunday() {
        // given
        DateInfo sundayDate = new DateInfo(24);
        DateInfo nonSundayDate = new DateInfo(25);

        // when & then
        Assertions.assertThat(sundayDate.isSunday()).isTrue();
        Assertions.assertThat(nonSundayDate.isSunday()).isFalse();
    }

    @Test
    @DisplayName("createDateTimeFormat 메서드가 LocalDate 객체를 올바르게 생성할 수 있다.")
    public void testCreateDateTimeFormat() {
        // given
        DateInfo dateInfo = new DateInfo(25);

        // when
        LocalDate localDate = dateInfo.createDateTimeFormat();

        // then
        Assertions.assertThat(localDate.getYear()).isEqualTo(2023);
        Assertions.assertThat(localDate.getMonthValue()).isEqualTo(12);
        Assertions.assertThat(localDate.getDayOfMonth()).isEqualTo(25);
    }

    @Test
    @DisplayName("toString 메서드가 포맷화된 날짜 문자열을 올바르게 반환할 수 있다.")
    public void testToString() {
        // given
        DateInfo dateInfo = new DateInfo(25);
        String expectedString = "12월 25일";

        // when & then
        Assertions.assertThat(dateInfo.toString()).isEqualTo(expectedString);
    }
}
