package app.model;

import app.dao.DataRepository;

import java.util.concurrent.atomic.AtomicInteger;

import static app.view.ContentView.printData;

public class DataHandlerAtomicCounter implements DataHandler {

    String[] fruits = new DataRepository().getData();
    AtomicInteger count = new AtomicInteger(1);

    @Override
    public void getOutput() {
        StringBuilder sb = new StringBuilder();
        for (String fruit : fruits) {
            sb.append(String.format("(%d) %s ",
                    count.getAndAdd(1), fruit));
        }
        printData(sb.toString());
    }

}
