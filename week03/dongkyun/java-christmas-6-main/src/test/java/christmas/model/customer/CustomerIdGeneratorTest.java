package christmas.model.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerIdGeneratorTest {

    @Test
    @DisplayName("id가 1씩 증가해야 한다")
    public void testGenerateId() {
        // given
        long id1 = CustomerIdGenerator.generateId();
        long id2 = CustomerIdGenerator.generateId();

        // then
        assertThat(id2).isEqualTo(id1 + 1);
    }
}