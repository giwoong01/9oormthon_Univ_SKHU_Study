package christmas.model.order;

import christmas.model.menu.Menu;

public record Order(Menu menu, int count) {
    public static Order from(String inputMenu, int inputCount) {
        Menu menu = Menu.from(inputMenu);

        return new Order(menu, inputCount);
    }
}
