package christmas.model.menu;

import christmas.message.ErrorMessage;
import java.util.Arrays;

public enum Menu {
    MUSHROOM_SOUP("양송이수프", MenuCategory.APPETIZER, 6000),
    TAPAS("타파스", MenuCategory.APPETIZER, 5500),
    CAESAR_SALAD("시저샐러드", MenuCategory.APPETIZER, 8000),

    T_BONE_STEAK("티본스테이크", MenuCategory.MAIN, 55000),
    BBQ_RIBS("바비큐립", MenuCategory.MAIN, 54000),
    SEAFOOD_PASTA("해산물파스타", MenuCategory.MAIN, 35000),
    CHRISTMAS_PASTA("크리스마스파스타", MenuCategory.MAIN, 25000),

    CHOCOLATE_CAKE("초코케이크", MenuCategory.DESSERT, 15000),
    ICE_CREAM("아이스크림", MenuCategory.DESSERT, 5000),

    ZERO_COLA("제로콜라", MenuCategory.DRINK, 3000),
    RED_WINE("레드와인", MenuCategory.DRINK, 60000),
    CHAMPAGNE("샴페인", MenuCategory.DRINK, 25000);

    private final String name;
    private final MenuCategory menuCategory;
    private final int price;

    Menu(String name, MenuCategory menuCategory, int price) {
        this.name = name;
        this.menuCategory = menuCategory;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public MenuCategory getCategory() {
        return menuCategory;
    }

    public int getPrice() {
        return price;
    }

    public static Menu from(String menu) {
        return Arrays.stream(Menu.values())
                .filter(element -> element.name.equals(menu))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.INVALID_ORDER_ERROR.getMessage()));
    }
}
