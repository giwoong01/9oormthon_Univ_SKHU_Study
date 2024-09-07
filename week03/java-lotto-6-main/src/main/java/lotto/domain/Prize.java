package lotto.domain;

public enum Prize {
    FIRST(6, false, 2000000000, "1등"),
    SECOND(5, true, 30000000, "2등"),
    THIRD(5, false, 1500000, "3등"),
    FOURTH(4, false, 50000, "4등"),
    FIFTH(3, false, 5000, "5등"),
    NONE(0, false, 0, "꽝");

    private final int matchCount;
    private final boolean bonusMatch;
    private final int prizeMoney;
    private final String description;

    Prize(int matchCount, boolean bonusMatch, int prizeMoney, String description) {
        this.matchCount = matchCount;
        this.bonusMatch = bonusMatch;
        this.prizeMoney = prizeMoney;
        this.description = description;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public String getDescription() {
        return description;
    }

    public static Prize valueOf(int matchCount, boolean bonusMatch) {
        for (Prize prize : values()) {
            if (prize.matchCount == matchCount && prize.bonusMatch == bonusMatch) {
                return prize;
            }
        }
        if (matchCount < 3) {
            return NONE;
        }
        return null;
    }
}
