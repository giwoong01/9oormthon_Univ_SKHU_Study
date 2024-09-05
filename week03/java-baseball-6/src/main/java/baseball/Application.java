package baseball;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) {
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

    private static List<Integer> generateRandomNumbers() {
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        return computer;
    }

    private static void playGame() {
        System.out.println("숫자 야구 게임을 시작합니다.");
        List<Integer> computer = generateRandomNumbers();

        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            String userInput = Console.readLine();

            validateInput(userInput);


            List<Integer> userNumbers = parseInput(userInput);
            int[] result = calculateScore(computer, userNumbers);

            if (result[1] == 3) {
                System.out.println("3스트라이크");
                System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
                break;
            } else {
                printResult(result);
            }
        }
    }

    private static void validateInput(String input) {
        if (input.length() != 3) {
            throw new IllegalArgumentException("1~9까지의 숫자 3개를 입력해야 합니다.");
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < '1' || c > '9') {
                throw new IllegalArgumentException("1~9까지의 숫자 3개를 입력해야 합니다.");
            }
        }

        if (input.charAt(0) == input.charAt(1) || input.charAt(1) == input.charAt(2) || input.charAt(0) == input.charAt(2)) {
            throw new IllegalArgumentException("숫자는 서로 달라야 합니다.");
        }
    }

    private static List<Integer> parseInput(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (char c : input.toCharArray()) {
            numbers.add(Character.getNumericValue(c));
        }
        return numbers;
    }

    private static int[] calculateScore(List<Integer> computer, List<Integer> userNumbers) {
        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < 3; i++) {
            if (computer.get(i).equals(userNumbers.get(i))) {
                strikes++;
            } else if (computer.contains(userNumbers.get(i))) {
                balls++;
            }
        }

        return new int[]{balls, strikes};
    }

    private static void printResult(int[] result) {
        int balls = result[0];
        int strikes = result[1];

        if (balls == 0 && strikes == 0) {
            System.out.println("낫싱");
        } else {
            StringBuilder sb = new StringBuilder();
            if (balls > 0) {
                sb.append(balls).append("볼 ");
            }
            if (strikes > 0) {
                sb.append(strikes)
                        .append("스트라이크");
            }
            System.out.println(sb.toString().trim());
        }
    }
}
