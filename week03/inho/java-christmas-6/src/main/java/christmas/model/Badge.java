package christmas.model;

import christmas.constant.event.BadgeName;

public class Badge {

    private final BadgeName badgeName;
    private static final int BADGE_LEVEL_1_VALUE = 5000;
    private static final int BADGE_LEVEL_2_VALUE = 10000;
    private static final int BADGE_LEVEL_3_VALUE = 20000;

    public Badge(int eventCost) {
        badgeName = getBadgeName(eventCost);
    }

    private BadgeName getBadgeName(int eventCost) {
        if (eventCost < BADGE_LEVEL_1_VALUE)
            return BadgeName.NO_BADGE;

        if (eventCost < BADGE_LEVEL_2_VALUE)
            return BadgeName.LEVEL_1;

        if (eventCost < BADGE_LEVEL_3_VALUE)
            return BadgeName.LEVEL_2;

        return BadgeName.LEVEL_3;
    }

    public String getBadgeName() {
        return badgeName.badgeName();
    }
}
