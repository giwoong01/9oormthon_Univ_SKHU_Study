package lotto.view;

import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;


public class InputView {

    private static final String INPUT_START_MESSAGE = "구입금액을 입력해 주세요.";
    private static final String INPUT_PICK_NUMBER_MESSAGE = "당첨 번호를 입력해주세요.";
    private static final String INPUT_BONUS_NUMBER_MESSAGE = "보너스 번호를 입력해 주세요.";


    public InputView() {
    }

    public int printStartMessage(){
        System.out.println(INPUT_START_MESSAGE);
        return toInt(Console.readLine());

    }
    private int toInt(String input){
        try {
            return Integer.parseInt(input);
        }
        catch (NumberFormatException e){
            throw new IllegalArgumentException("숫자를 입력해주세요.");
        }
    }

    public List<Integer> inputPickNumber(){
        System.out.println(INPUT_PICK_NUMBER_MESSAGE);
        return convertPickNumber(Console.readLine());
    }

    private List<Integer> convertPickNumber(String input){
        List<Integer> pickNumbers = new ArrayList<>();
        String[] numbers = input.split(",");

        for(String number : numbers){
            pickNumbers.add(Integer.parseInt(number.trim()));
        }
        return pickNumbers;
    }

    public Integer inputBonusNumber(){
        System.out.println(INPUT_BONUS_NUMBER_MESSAGE);
        return toInt(Console.readLine());
    }
}
