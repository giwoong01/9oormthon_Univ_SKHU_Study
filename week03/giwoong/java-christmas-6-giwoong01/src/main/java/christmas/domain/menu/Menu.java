package christmas.domain.menu;

import java.text.NumberFormat;

public enum Menu {
    APPETIZER_MUSHROOM_SOUP("양송이수프", 6_000, MenuCategory.APPETIZER),
    APPETIZER_TAPAS("타파스", 5_500, MenuCategory.APPETIZER),
    APPETIZER_CAESAR_SALAD("시저샐러드", 8_000, MenuCategory.APPETIZER),

    MAIN_T_BONE_STEAK("티본스테이크", 55_000, MenuCategory.MAIN),
    MAIN_BARBECUE_RIB("바비큐립", 54_000, MenuCategory.MAIN),
    MAIN_SEA_FOOD_PASTA("해산물파스타", 35_000, MenuCategory.MAIN),
    MAIN_CHRISTMAS_PASTA("크리스마스파스타", 25_000, MenuCategory.MAIN),

    DESSERT_CHOCOLATE_CAKE("초코케이크", 15_000, MenuCategory.DESSERT),
    DESSERT_ICE_CREAM("아이스크림", 5_000, MenuCategory.DESSERT),

    DRINK_ZERO_COLA("제로콜라", 3_000, MenuCategory.DRINK),
    DRINK_RED_WINE("레드와인", 60_000, MenuCategory.DRINK),
    DRINK_CHAMPAGNE("샴페인", 25_000, MenuCategory.DRINK);

    private final String name;
    private final int price;
    private final MenuCategory category;

    Menu(String name, int price, MenuCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public MenuCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        return numberFormat.format(price);
    }
}
