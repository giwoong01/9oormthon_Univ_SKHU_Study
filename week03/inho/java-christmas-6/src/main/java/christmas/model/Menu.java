package christmas.model;

import christmas.constant.menu.MenuConstant;
import christmas.constant.menu.MenuKind;

public class Menu {

    private MenuConstant menuConstant;
    private final int size;

    public Menu(String menuName, int size) {
        if(!checkMenuKind(menuName))
            throw new IllegalArgumentException();

        this.size = size;
    }

    private boolean checkMenuKind(String menuName) {
        MenuConstant menuConstant = MenuConstant.findMenuByStringManuName(menuName);
        this.menuConstant = menuConstant;

        return menuConstant != null;
    }

    public int getSize() {
        return size;
    }

    public boolean checkManuKind(MenuKind menuKind) {
        return menuKind == menuConstant.getMenuKind();
    }

    public int getManuPrice() {
        return menuConstant.getManuPrice() * size;
    }

    @Override
    public String toString() {
        return menuConstant.getMenuName() + " "  + size + "개";
    }
}
