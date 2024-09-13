package christmas.validator;

import christmas.model.manager.EventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuValidatorTest {

    private MenuValidator menuValidator;

    @BeforeEach
    void setUp() {
        menuValidator = new MenuValidator();
    }

    @Test
    void 메뉴_주문_목록이_올바르면_true() {
        boolean isValid = menuValidator.validateMenu("티본스테이크-1,바비큐립-1");

        assertThat(isValid).isTrue();
    }

    @Test
    void 메뉴_이름이_아니면_false() {
        boolean isValid = menuValidator.validateMenu("귯걸-1,바비큐립-1");

        assertThat(isValid).isFalse();
    }

    @Test
    void 메뉴_개수가_0이면_false() {
        boolean isValid = menuValidator.validateMenu("티본스테이크-0,바비큐립-1");

        assertThat(isValid).isFalse();
    }

    @Test
    void 메뉴_개수가_음수면_false() {
        boolean isValid = menuValidator.validateMenu("티본스테이크--1,바비큐립-1");

        assertThat(isValid).isFalse();
    }
}
