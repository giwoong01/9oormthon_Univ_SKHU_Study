package christmas.view;

import static christmas.message.ErrorMessage.INVALID_DATE_ERROR_MESSAGE;
import static christmas.message.ErrorMessage.INVALID_ORDER_ERROR_MESSAGE;
import static christmas.message.MessageConstants.COMMA;
import static christmas.message.MessageConstants.EXPECTED_VISIT_DATE_PROMPT;
import static christmas.message.MessageConstants.GREETING_EVENT_PLANNER;
import static christmas.message.MessageConstants.HYPHEN;
import static christmas.message.MessageConstants.ONE;
import static christmas.message.MessageConstants.ORDER_MENU_PROMPT;
import static christmas.message.MessageConstants.THIRTY_ONE;
import static christmas.message.MessageConstants.TWO;
import static christmas.message.MessageConstants.ZERO;

import camp.nextstep.edu.missionutils.Console;
import christmas.domain.Buyer;
import christmas.domain.MenuAndCount;
import christmas.domain.MenuAndCounts;
import java.util.ArrayList;
import java.util.List;

public class InputView {
    public int inputVisitDate() {
        while (true) {
            try {
                System.out.println(GREETING_EVENT_PLANNER);
                System.out.println(EXPECTED_VISIT_DATE_PROMPT);
                String inputDate = Console.readLine().trim();

                validateNumericInput(inputDate);
                validateDateRange(Integer.parseInt(inputDate));
                return Integer.parseInt(inputDate);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateNumericInput(String date) {
        try {
            Integer.parseInt(date);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_DATE_ERROR_MESSAGE);
        }
    }

    private void validateDateRange(int date) {
        if (date < ONE || date > THIRTY_ONE) {
            throw new IllegalArgumentException(INVALID_DATE_ERROR_MESSAGE);
        }
    }

    public Buyer inputMenuAndCounts() {
        while (true) {
            try {
                System.out.println(ORDER_MENU_PROMPT);
                String[] inputMenuAndCounts = Console.readLine().split(COMMA);
                List<MenuAndCount> menuAndCounts = createMenuAndCounts(inputMenuAndCounts);

                return new Buyer(new MenuAndCounts(menuAndCounts));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<MenuAndCount> createMenuAndCounts(String[] inputMenuAndCounts) {
        List<MenuAndCount> menuAndCounts = new ArrayList<>();
        for (String menuAndCount : inputMenuAndCounts) {
            String[] split = validateOrderFormat(menuAndCount);
            String menuName = split[ZERO].trim();
            String count = split[ONE].trim();

            menuAndCounts.add(new MenuAndCount(menuName, count));
        }

        return menuAndCounts;
    }

    private String[] validateOrderFormat(String menuAndCount) {
        String[] split = menuAndCount.split(HYPHEN);

        if (split.length != TWO) {
            throw new IllegalArgumentException(INVALID_ORDER_ERROR_MESSAGE);
        }

        return split;
    }
}
