package christmas.model.event;

public enum Badge {
    NOTHING("없음", 0),
    STAR("별", 5_000),
    TREE("트리", 10_000),
    SANTA("산타", 20_000);

    private final String name;
    private final int threshold;

    Badge(String name, int threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public String getName() {
        return name;
    }

    public int getThreshold() {
        return threshold;
    }


    public static Badge fromDiscount(int discountAmount) {
        return getBadgeForAmount(discountAmount);
    }

    private static Badge getBadgeForAmount(int amount) {
        return checkForSantaBadge(amount);
    }

    private static Badge checkForSantaBadge(int amount) {
        if (amount >= SANTA.getThreshold()) {
            return SANTA;
        }
        return checkForTreeBadge(amount);
    }

    private static Badge checkForTreeBadge(int amount) {
        if (amount >= TREE.getThreshold()) {
            return TREE;
        }
        return checkForStarBadge(amount);
    }

    private static Badge checkForStarBadge(int amount) {
        if (amount >= STAR.getThreshold()) {
            return STAR;
        }
        return NOTHING;
    }
}
