package christmas.domain;

import static christmas.message.ErrorMessage.DRINKS_ONLY_ORDER_ERROR_MESSAGE;
import static christmas.message.ErrorMessage.INVALID_ORDER_ERROR_MESSAGE;
import static christmas.message.ErrorMessage.ORDER_SIZE_LIMIT_ERROR_MESSAGE;
import static christmas.message.MessageConstants.NEW_LINE;
import static christmas.message.MessageConstants.TWENTY;
import static christmas.message.MessageConstants.ZERO;

import christmas.domain.menu.Menu;
import christmas.domain.menu.MenuCategory;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MenuAndCounts {
    private final List<MenuAndCount> menuAndCounts;
    private final int totalCount;

    public MenuAndCounts(List<MenuAndCount> menuAndCounts) {
        this.menuAndCounts = menuAndCounts;
        validateIncludesFoodOrder();

        validateDuplicateMenu();
        this.totalCount = menuAndCounts.stream()
                .mapToInt(MenuAndCount::getCount)
                .sum();
        validateMaxMenuOrderCount();
    }

    private void validateIncludesFoodOrder() {
        boolean hasFoodOrder = menuAndCounts.stream()
                .anyMatch(menuAndCount -> Arrays.stream(Menu.values())
                        .filter(menu -> menu.getName().equals(menuAndCount.getMenuName()))
                        .anyMatch(menu -> menu.getCategory() != MenuCategory.DRINK));

        if (!hasFoodOrder) {
            throw new IllegalArgumentException(DRINKS_ONLY_ORDER_ERROR_MESSAGE);
        }
    }

    private void validateDuplicateMenu() {
        long uniqueMenuCount = menuAndCounts.stream()
                .map(MenuAndCount::getMenuName)
                .distinct()
                .count();

        if (uniqueMenuCount != menuAndCounts.size()) {
            throw new IllegalArgumentException(INVALID_ORDER_ERROR_MESSAGE);
        }
    }

    private void validateMaxMenuOrderCount() {
        if (totalCount > TWENTY) {
            throw new IllegalArgumentException(ORDER_SIZE_LIMIT_ERROR_MESSAGE);
        }
    }

    public int calculateTotalPrice() {
        return menuAndCounts.stream()
                .mapToInt(this::calculateMenuPrice)
                .sum();
    }

    private int calculateMenuPrice(MenuAndCount menuAndCount) {
        String menuName = menuAndCount.getMenuName();
        int count = menuAndCount.getCount();

        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getName().equals(menuName))
                .findFirst()
                .map(menu -> menu.getPrice() * count)
                .orElse(ZERO);
    }

    public List<MenuAndCount> getMenuAndCounts() {
        return Collections.unmodifiableList(menuAndCounts);
    }

    @Override
    public String toString() {
        return menuAndCounts.stream()
                .map(MenuAndCount::toString)
                .collect(Collectors.joining(NEW_LINE));
    }

}
