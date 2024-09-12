package christmas.model.calender;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DateInfo {
    public static final int YEAR = 2023;
    public static final int MONTH = 12;
    private static final String DATE_FORMAT = "%d월 %d일";

    private final Year year;
    private final Month month;
    private final Day day;

    public DateInfo(int day) {
        this.year = new Year(YEAR);
        this.month = new Month(MONTH);
        this.day = new Day(day);
    }

    public boolean isWeekend() {
        LocalDate visitDay = createDateTimeFormat();
        DayOfWeek dayOfWeek = visitDay.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    public boolean isSunday() {
        LocalDate visitDay = createDateTimeFormat();
        DayOfWeek dayOfWeek = visitDay.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SUNDAY;
    }

    public LocalDate createDateTimeFormat() {
        return LocalDate.of(getYear(), getMonth(), getDay());
    }

    @Override
    public String toString() {
        return String.format(DATE_FORMAT, month.month(), day.day());
    }

    public int getYear() {
        return year.year();
    }

    public int getMonth() {
        return month.month();
    }

    public int getDay() {
        return day.day();
    }
}
