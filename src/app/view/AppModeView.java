package app.view;

import app.enums.AppMode;
import app.exceptions.InputException;

import java.util.Scanner;

import static app.utils.AppModeTitle.printTitle;
import static app.utils.InputValidator.validateAppModeType;

public class AppModeView {

    public static AppMode getAppMode() {
        printTitle();
        String operation;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            operation = scanner.nextLine().strip();
            try {
                return validateAppModeType(operation);
            } catch (InputException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
