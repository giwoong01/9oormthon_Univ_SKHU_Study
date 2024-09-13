package lotto.model;

import lotto.view.OutputView;

public enum Rank {
    FIRST("6개 일치 (2,000,000,000원)", 6 ,2_000_000_000),
    SECOND("5개 일치, 보너스 볼 일치 (30,000,000원)", 5, 30_000_000),
    THIRD("5개 일치 (1,500,000원)", 5, 1_500_000),
    FOURTH("4개 일치 (50,000원)", 4, 50_000),
    FIFTH("3개 일치 (5,000원)", 3, 5_000),
    NONE("낙첨", 0, 0);

    Rank(String resultMsg, int winCount, int winPrice) {
        this.resultMsg = resultMsg;
        this.winCount = winCount;
        this.winPrice = winPrice;
    }

    private final String resultMsg;
    private final int winCount;
    private final int winPrice;

    private boolean matchCount(int count) {
        return getWinCount() == count;
    }

    public static void printResultMsg(Rank rank,int count) {
        if (rank != NONE) {
            OutputView.printWinningStatistics(rank.resultMsg, count);
        }
    }

    public int getWinPrice() {
        return winPrice;
    }
    public int getWinCount() {
        return winCount;
    }

    public static Rank provideRank(int winCount, boolean matchBonus) {
        if (winCount < FIFTH.winCount) {
            return NONE;
        }
        if (THIRD.matchCount(winCount) && matchBonus) {
            return SECOND;
        }
        for (Rank rank : values()) {
            if (rank.matchCount(winCount) && rank != SECOND) {
                return rank;
            }
        }
        throw new IllegalArgumentException("[ERROR]");
    }
}
