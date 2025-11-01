package app.view;

public class ContentView {

    public static synchronized void printData(String data) {
        System.out.println(Thread.currentThread().getName() + ": " + data);
    }
}
