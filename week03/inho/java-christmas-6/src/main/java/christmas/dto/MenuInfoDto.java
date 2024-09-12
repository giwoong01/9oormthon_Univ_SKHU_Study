package christmas.dto;

public class MenuInfoDto {

    private final String menuName;
    private final int size;

    public MenuInfoDto(String menuName, int size) {
        this.menuName = menuName;
        this.size = size;
    }

    @Override
    public String toString() {
        return menuName + " " + size + "개";
    }
}
