package christmas.constant.event;

public enum BadgeName {
    NO_BADGE("없음"),
    LEVEL_1("별"),
    LEVEL_2("트리"),
    LEVEL_3("산타");

    private final String badgeName;

    BadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String badgeName() {
        return badgeName;
    }
}
