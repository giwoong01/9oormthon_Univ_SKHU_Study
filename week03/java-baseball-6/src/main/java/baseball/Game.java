package baseball;

import camp.nextstep.edu.missionutils.Console;

public class Game {

    public void run() {
        while (true) {
            playGame();
            System.out.print("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요. ");
            String input = Console.readLine();

            if (input.equals("2")) {
                System.out.println("게임을 종료합니다.");
                break;
            } else if (!input.equals("1")) {
                throw new IllegalArgumentException("1 또는 2를 입력해야 합니다.");
            }
        }
    }

    private void playGame() {
        System.out.println("숫자 야구 게임을 시작합니다.");
        Number number = new Number();
        Validator validator = new Validator();
        Result result = new Result();

        var computerNumbers = number.generateRandomNumbers();

        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            String userInput = Console.readLine();

            validator.validateInput(userInput); // 입력 검증
            var userNumbers = number.parseInput(userInput); // 입력 변환
            var gameResult = result.calculateScore(computerNumbers, userNumbers); // 결과 계산

            if (gameResult[1] == 3) { // 3스트라이크면 게임 종료
                System.out.println("3스트라이크");
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                break;
            } else {
                result.printResult(gameResult); // 결과 출력
            }
        }
    }
}
