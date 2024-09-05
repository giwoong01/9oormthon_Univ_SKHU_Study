package christmas.util;

import christmas.dto.order.OrdersDto;
import christmas.message.ErrorMessage;
import christmas.model.menu.Menu;
import christmas.model.menu.MenuCategory;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class RetryUtil {
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;
    private static final int MIN_COUNT = 1;
    private static final String HYPHEN = "-";

    private RetryUtil() {
    }

    public static int retryReadDate(Supplier<Integer> supplier) {
        while (true) {
            try {
                int day = supplier.get();
                validateRangeFromDay(day);
                return day;
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.INVALID_DAY_ERROR.getMessage());
            }
        }
    }

    private static void validateRangeFromDay(int day) {
        if (day < MIN_DAY || day > MAX_DAY) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DAY_ERROR.getMessage());
        }
    }

    public static OrdersDto retryReadMenu(Supplier<String[]> supplier) {
        while (true) {
            try {
                String[] orders = supplier.get();
                validateOrders(orders);
                return OrdersDto.from(orders);
            } catch (IllegalArgumentException e) {
                System.out.println(ErrorMessage.INVALID_ORDER_ERROR.getMessage());
            }
        }
    }

    private static void validateOrders(String[] orders) {
        Set<String> seenMenus = new HashSet<>();
        boolean hasNonDrink = false;

        for (String order : orders) {
            validateOrderFormat(order);
            String menuName = extractMenuName(order);
            int count = extractCount(order);

            validateMenuExists(menuName);
            validateCount(count);
            validateDuplicateMenu(seenMenus, menuName);
            hasNonDrink = checkIfNonDrinkMenu(menuName) || hasNonDrink;
        }

        validateAllMenuIsDrink(hasNonDrink);
    }

    private static void validateOrderFormat(String order) {
        String[] menuAndCount = order.split(HYPHEN);
        if (menuAndCount.length != 2) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_ERROR.getMessage());
        }
    }

    private static String extractMenuName(String order) {
        return order.split(HYPHEN)[0].trim();
    }

    private static int extractCount(String order) {
        try {
            return Integer.parseInt(order.split(HYPHEN)[1].trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_COUNT_ERROR.getMessage());
        }
    }

    private static void validateMenuExists(String menuName) {
        try {
            Menu.from(menuName);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_ERROR.getMessage());
        }
    }

    private static void validateCount(int count) {
        if (count < MIN_COUNT) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_COUNT_ERROR.getMessage());
        }
    }

    private static void validateDuplicateMenu(Set<String> seenMenus, String menuName) {
        if (!seenMenus.add(menuName)) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_MENU_ERROR.getMessage());
        }
    }

    private static boolean checkIfNonDrinkMenu(String menuName) {
        Menu menu = Menu.from(menuName);
        return menu.getCategory() != MenuCategory.DRINK;
    }

    private static void validateAllMenuIsDrink(boolean hasNonDrink) {
        if (!hasNonDrink) {
            throw new IllegalArgumentException(ErrorMessage.NON_DRINK_MENU_REQUIRED_ERROR.getMessage());
        }
    }
}
