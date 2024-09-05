package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.message.InputMessage;

public class InputView {
    private static final InputView INSTANCE = new InputView();

    private InputView() {
    }

    public static InputView getInstance() {
        return INSTANCE;
    }

    public int readDate() {
        System.out.println(InputMessage.DATE_PROMPT.getMessage());
        return Integer.parseInt(Console.readLine());
    }

    public String[] readMenu() {
        System.out.println(InputMessage.MENU_PROMPT.getMessage());
        return Console.readLine().split(",");
    }
}
