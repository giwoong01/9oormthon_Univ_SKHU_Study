package christmas.domain;

import static christmas.message.ErrorMessage.INVALID_ORDER_ERROR_MESSAGE;
import static christmas.message.MessageConstants.MENU_NAME_AND_COUNT;
import static christmas.message.MessageConstants.ONE;

import christmas.domain.menu.Menu;
import java.util.Arrays;

public class MenuAndCount {
    private final String menuName;
    private final int count;

    public MenuAndCount(String menuName, String count) {
        validateNotExistsMenu(menuName);
        this.menuName = menuName;

        validateCountIsNumber(count);
        validateCountIsPositive(Integer.parseInt(count));
        this.count = Integer.parseInt(count);
    }

    private void validateNotExistsMenu(String menuName) {
        boolean isExists = Arrays.stream(Menu.values())
                .anyMatch(menu -> menu.getName().equals(menuName));

        if (!isExists) {
            throw new IllegalArgumentException(INVALID_ORDER_ERROR_MESSAGE);
        }
    }

    private void validateCountIsPositive(int count) {
        if (count < ONE) {
            throw new IllegalArgumentException(INVALID_ORDER_ERROR_MESSAGE);
        }
    }

    private void validateCountIsNumber(String count) {
        try {
            Integer.parseInt(count);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_ORDER_ERROR_MESSAGE);
        }
    }

    public String getMenuName() {
        return menuName;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format(MENU_NAME_AND_COUNT, menuName, count);
    }
}
