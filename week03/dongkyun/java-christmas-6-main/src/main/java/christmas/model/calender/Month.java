package christmas.model.calender;

public record Month(int month) {
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;

    public Month {
        validateRangeFromMonth(month);
    }

    private void validateRangeFromMonth(int day) {
        if (day < MIN_MONTH || day > MAX_MONTH) {
            throw new IllegalArgumentException("없는 월입니다");
        }
    }
}