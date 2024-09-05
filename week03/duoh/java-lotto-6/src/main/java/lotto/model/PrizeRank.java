package lotto.model;

public enum PrizeRank {
    FIRST_PRIZE(2_000_000_000, 6),
    SECOND_PRIZE(30_000_000, 5, true),
    THIRD_PRIZE(1_500_000, 5),
    FOURTH_PRIZE(50_000, 4),
    FIFTH_PRIZE(5_000, 3),
    MISS(0, 0);

    private final int prize;
    private final int matchCount;
    private final boolean bonus;

    PrizeRank(int prize, int matchCount) {
        this(prize, matchCount, false);
    }

    PrizeRank(int prize, int matchCount, boolean bonus) {
        this.prize = prize;
        this.matchCount = matchCount;
        this.bonus = bonus;
    }

    public int getPrize() {
        return prize;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public static PrizeRank from(int matchCount, boolean bonusMatch) {
        if (matchCount == 6) {
            return FIRST_PRIZE;
        }

        if (matchCount == 5 && bonusMatch) {
            return SECOND_PRIZE;
        }

        if (matchCount == 5) {
            return THIRD_PRIZE;
        }

        if (matchCount == 4) {
            return FOURTH_PRIZE;
        }

        if (matchCount == 3) {
            return FIFTH_PRIZE;
        }

        return MISS;
    }
}
