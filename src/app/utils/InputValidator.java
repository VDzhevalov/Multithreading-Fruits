package app.utils;

import app.enums.AppMode;
import app.exceptions.InputException;

public class InputValidator {

    public static AppMode validateAppModeType(String operationType) throws InputException {
        try {
            return AppMode.values()[Integer.parseInt(operationType) - 1];
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            throw new InputException("App mode must be a valid mode number.");
        }
    }
}
