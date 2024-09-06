package baseball.controller;

import baseball.model.BaseballReferee;
import baseball.model.InputValidator;
import baseball.view.InputView;
import baseball.view.OutputView;

import java.util.*;


public class BaseballGame {

    private final BaseballReferee baseballReferee;
    private final OutputView outputView;
    private final InputView inputView;
    private final InputValidator inputValidator;

    public BaseballGame() {
        this.baseballReferee = new BaseballReferee();
        this.outputView = new OutputView();
        this.inputView = new InputView();
        this.inputValidator = new InputValidator();
    }

    public void settingGame() {
        outputView.printStartGame();
        playGame();

        if (askRestart())
            settingGame();
    }

    public void playGame() {
        List<Integer> computerNumbers = baseballReferee.generateComputerNumbers();
        while (true) {
            List<Integer> userNumbers = getUserNumbers();

            String result = baseballReferee.getResult(computerNumbers, userNumbers);
            outputView.printResult(result);

            if (result.equals("3스트라이크")) {
                outputView.printEndGame();
                break;
            }
        }
    }

    private List<Integer> getUserNumbers() {
        String input = getUserInput();
        return convertInputType(input);
    }

    public String getUserInput() {
        outputView.printInputNumber();
        String input = inputView.inputNumber();
        inputValidator.validateInput(input);
        return input;
    }

    public boolean askRestart() {
        outputView.printRestartGame();
        String input = inputView.inputNumber();

        if (!input.equals("1") && !input.equals("2"))
            throw new IllegalArgumentException("잘못된 입력입니다.");

        return input.equals("1");
    }

    public List<Integer> convertInputType(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (String num : input.split(""))
            numbers.add(Integer.parseInt(num));

        return numbers;
    }
}