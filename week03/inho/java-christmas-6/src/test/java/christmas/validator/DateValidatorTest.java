package christmas.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateValidatorTest {

    private DateValidator dateValidator;

    @BeforeEach
    void setUp() {
        dateValidator = new DateValidator();
    }

    @Test
    void 주문_날짜가_int형이면_true() {
        boolean isValid = dateValidator.validateDate("15");

        assertThat(isValid).isTrue();
    }

    @Test
    void 주문_날짜가_int형이_아니면_false() {
        boolean isValid = dateValidator.validateDate("귯걸");

        assertThat(isValid).isFalse();
    }

    @Test
    void 주문_날짜가_1인_경우_true() {
        boolean isValid = dateValidator.validateDate("1");

        assertThat(isValid).isTrue();
    }

    @Test
    void 주문_날짜가_31인_경우_true() {
        boolean isValid = dateValidator.validateDate("31");

        assertThat(isValid).isTrue();
    }

    @Test
    void 주문_날짜가_32인_경우_false() {
        boolean isValid = dateValidator.validateDate("32");

        assertThat(isValid).isFalse();
    }

    @Test
    void 주문_날짜가_음수인_경우_false() {
        boolean isValid = dateValidator.validateDate("-1");

        assertThat(isValid).isFalse();
    }
}
