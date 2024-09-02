package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.model.Menu;
import christmas.validator.DateValidator;
import christmas.validator.MenuValidator;

import java.util.List;

public class InputView {

    private final MenuValidator menuValidator;
    private final DateValidator dateValidator;

    public InputView() {
        menuValidator = new MenuValidator();
        dateValidator = new DateValidator();
    }

    public int readExpectedVisitDate() {
        String date;

        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.print("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");

        do {
            System.out.println(dateValidator.getErrorMessage());
            date = Console.readLine();
        } while (!dateValidator.validateDate(date));

        return dateValidator.getIntegerDate();
    }

    public List<Menu> readMenusAndNumbers() {
        String date;

        System.out.print("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");

        do {
            System.out.println(menuValidator.getErrorMessage());
            date = Console.readLine();
        } while (!menuValidator.validateMenu(date));

        return menuValidator.getMenus();
    }
}
