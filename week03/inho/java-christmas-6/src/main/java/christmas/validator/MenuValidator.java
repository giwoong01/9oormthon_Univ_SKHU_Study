package christmas.validator;


import christmas.constant.ErrorMessage;
import christmas.constant.menu.MenuKind;
import christmas.model.Menu;

import java.util.*;

public class MenuValidator {

    private LinkedHashMap<String, Integer> orders;
    private List<Menu> menus;
    private ErrorMessage errorMessage;
    private static final int MIN_ORDER = 1;
    private static final int MAX_ORDER = 20;
    private static final int SPLIT_LIMIT = -1; // 실제 split 메서드 안에 두 번째 매개변수 이름

    public boolean validateMenu(String otherString) {
        orders = new LinkedHashMap<>();
        menus = new ArrayList<>();

        otherString = otherString.replaceAll(" ", "");

        return validOtherString(otherString) && validContainUsingMenu(orders) &&
                validLessMinOverMax(menus) && validOrderOnlyDrink(menus);
    }

    private boolean validOtherString(String otherString) {
        try {
            parsingAndSettingOrderString(otherString);
        } catch (IllegalArgumentException e) {
            errorMessage = ErrorMessage.ERROR_ORDER;

            return false;
        }
        return true;
    }

    private void parsingAndSettingOrderString(String otherString) {
        List<String> orderStrings = Arrays.stream(otherString.split(",", SPLIT_LIMIT)).toList();
        orderStrings.forEach(this::settingOrderMap);
    }

    private void settingOrderMap(String orderString) {
        String[] order = orderString.split("-", SPLIT_LIMIT);
        int size = Integer.parseInt(order[1]);

        if (size <= 0)
            throw new IllegalArgumentException();

        if (orders.containsKey(order[0]))
            throw new IllegalArgumentException();

        orders.put(order[0], size);
    }

    private boolean validContainUsingMenu(HashMap<String, Integer> orders) {
        try {
            orders.forEach((name, size) -> menus.add(new Menu(name, size)));
        } catch (IllegalArgumentException e) {
            errorMessage = ErrorMessage.ERROR_ORDER;

            return false;
        }
        return true;
    }

    private boolean validLessMinOverMax(List<Menu> menus) {
        int size = calculateTotalOrderSize(menus);

        try {
            if (size < MIN_ORDER || size > MAX_ORDER) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            errorMessage = ErrorMessage.ERROR_OVER_MAX_SIZE;

            return false;
        }
        return true;
    }

    private int calculateTotalOrderSize(List<Menu> menus) {
        return menus.stream()
                .mapToInt(Menu::getSize)
                .sum();
    }

    private boolean validOrderOnlyDrink(List<Menu> menus) {
        try {
            if (isAllDrinkMenu(menus)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            errorMessage = ErrorMessage.ERROR_OVER_ONLY_DRINK;

            return false;
        }
        return true;
    }

    private boolean isAllDrinkMenu(List<Menu> menus) {
        return menus.stream()
                .allMatch(menu -> menu.checkManuKind(MenuKind.DRINK));
    }


    public List<Menu> getMenus() {
        return Collections.unmodifiableList(menus);
    }

    public String getErrorMessage() {
        if (errorMessage == null){
            return "";
        }

        return errorMessage.toString();
    }
}
