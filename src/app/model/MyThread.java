package app.model;


public class MyThread<T extends DataHandler> extends Thread {

    T dataHandler;

    public MyThread(String name, T dataHandler) {
        super(name);
        this.dataHandler = dataHandler;
    }

    @Override
    public void run() {
        dataHandler.getOutput();
    }
}
