package christmas.validator;

import christmas.constant.ErrorMessage;

public class DateValidator {

    private int integerDate;
    private ErrorMessage errorMessage;
    public static final int MIN_DATE = 1;
    public static final int MAX_DATE = 31;

    public boolean validateDate(String date) {
        return validDateByStringDate(date) && validLessMinOverMaxByIntegerDate(integerDate);
    }

    private boolean validDateByStringDate(String stringDate) {
        try {
            integerDate = Integer.parseInt(stringDate);
        } catch (IllegalArgumentException e) {
            errorMessage = ErrorMessage.ERROR_DATE;

            return false;
        }
        return true;
    }

    private boolean validLessMinOverMaxByIntegerDate(int integerDate) {
        try {
            if (integerDate < MIN_DATE || integerDate > MAX_DATE) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            errorMessage = ErrorMessage.ERROR_DATE;

            return false;
        }
        return true;
    }

    public int getIntegerDate() {
        return integerDate;
    }

    public String getErrorMessage() {
        if (errorMessage == null) {
            return "";
        }
        return errorMessage.toString();
    }
}
