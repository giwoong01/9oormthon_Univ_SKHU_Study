package christmas.domain;

import java.util.List;

public class Buyer {
    private final MenuAndCounts menuAndCounts;

    public Buyer(MenuAndCounts menuAndCounts) {
        this.menuAndCounts = menuAndCounts;
    }

    public int getTotalPrice() {
        return menuAndCounts.calculateTotalPrice();
    }

    public List<MenuAndCount> getMenuAndCounts() {
        return menuAndCounts.getMenuAndCounts();
    }

    @Override
    public String toString() {
        return menuAndCounts.toString();
    }
}
