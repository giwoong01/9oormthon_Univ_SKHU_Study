package baseball;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Number {

    // 1~9 사이의 중복 x, 3개의 숫자 생성
    public List<Integer> generateRandomNumbers() {
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        return computer;
    }

    // 입력받은 문자열을 숫자 리스트로 변환
    public List<Integer> parseInput(String input) {
        List<Integer> numbers = new ArrayList<>();
        for (char c : input.toCharArray()) {
            numbers.add(Character.getNumericValue(c));
        }
        return numbers;
    }
}
