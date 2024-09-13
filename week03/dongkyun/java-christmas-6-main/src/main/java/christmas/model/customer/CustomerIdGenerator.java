package christmas.model.customer;

public class CustomerIdGenerator {
    private static long idCounter = 0;

    public static synchronized Long generateId() {
        return ++idCounter;
    }
}
