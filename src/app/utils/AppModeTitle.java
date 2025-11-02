package app.utils;

import app.enums.AppMode;

public class AppModeTitle {

    public static void printTitle() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Enter App Mode:\n");
        int counter = 0;
        for (AppMode appMode : AppMode.values()) {
            stringBuilder.append(++counter).append(") ").append(appMode.name()).append("\n");
        }
        System.out.println(stringBuilder);
    }
}
