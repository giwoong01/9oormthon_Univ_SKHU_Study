package christmas.domain;

import java.util.List;

public enum Date {
    WEEKEND(List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30)),
    WEEKDAY(List.of(3, 4, 5, 6, 7,
            10, 11, 12, 13, 14,
            17, 18, 19, 20, 21,
            24, 25, 26, 27, 28,
            31)),
    STAR(List.of(3, 10, 17, 24, 25, 31));

    private final List<Integer> dates;

    Date(List<Integer> dates) {
        this.dates = dates;
    }

    public List<Integer> getDates() {
        return dates;
    }
}
