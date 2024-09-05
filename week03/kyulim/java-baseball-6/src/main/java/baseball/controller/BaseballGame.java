package baseball.controller;

import baseball.model.BaseballReferee;
import baseball.view.InputView;
import baseball.view.OutputView;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;


public class BaseballGame {

    private final BaseballReferee baseballReferee;
    private final OutputView outputView;
    private final InputView inputView;

    public BaseballGame() {
        this.baseballReferee = new BaseballReferee();
        this.outputView = new OutputView();
        this.inputView = new InputView();
    }

    // 게임 세팅
    public void settingGame() {
        outputView.printStartGame();
        playGame();

        if (askRestart())
            settingGame();
    }

    // 게임 시작
    public void playGame() {
        List<Integer> computerNumbers = generateComputerNumbers();
        while (true) {
            String input = getUserInput();
            List<Integer> userNumbers = convertInputType(input);

            String result = baseballReferee.getResult(computerNumbers, userNumbers);
            outputView.printResult(result);

            if (result.equals("3스트라이크")) {
                outputView.printEndGame();
                break;
            }
        }
    }

    // 컴퓨터 숫자 랜덤으로 정하기
    public List<Integer> generateComputerNumbers() {
        Set<Integer> numberSet = new HashSet<>();
        while (numberSet.size() < 3) {
            numberSet.add(Randoms.pickNumberInRange(1, 9));
        }
        return new ArrayList<>(numberSet);
    }

    // 사용자 숫자 입력받기
    public String getUserInput() {
        outputView.printInputNumber();
        String input = inputView.inputNumber();
        validateInput(input);
        return input;
    }

    // 재시작 여부 묻기
    public boolean askRestart() {
        outputView.printRestartGame();
        String input = inputView.inputNumber();

        if (!input.equals("1") && !input.equals("2"))
            throw new IllegalArgumentException("잘못된 입력입니다.");

        return input.equals("1");
    }

    // 사용자 숫자 타입 변경 (String -> List<Integer>)
    public List<Integer> convertInputType(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (String num : input.split(""))
            numbers.add(Integer.parseInt(num));

        return numbers;
    }

    // 사용자 숫자 입력 형식 확인
    public void validateInput(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }

        for (char ch : input.toCharArray()) {
            if (ch < '1' || ch > '9') {
                throw new IllegalArgumentException("잘못된 입력입니다.");
            }
        }

        if (hasDuplicateCharacters(input)) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    // 사용자 숫자 입력 중복 확인(HashSet: 증복 허용X)
    public boolean hasDuplicateCharacters(String input) {
        return input.length() != new HashSet<>(Arrays.asList(input.split(""))).size();
    }


}
