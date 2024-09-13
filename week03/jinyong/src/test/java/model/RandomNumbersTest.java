package model;

import lotto.model.RandomNumbers;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomNumbersTest {

    @DisplayName("로또 번호 6자리를 무작위로 생성한다.")
    @Test
    void generateRandomNumbers() {
        List<Integer> listTest = RandomNumbers.generateRandomNumbers();
        Assertions.assertThat(listTest.size()).isEqualTo(6);
    }
}
