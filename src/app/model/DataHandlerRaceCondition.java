package app.model;

import app.dao.DataRepository;

import static app.view.ContentView.printData;

public class DataHandlerRaceCondition implements DataHandler {

    String[] fruits = new DataRepository().getData();
    int count = 1;

    @Override
    public void getOutput() {
        StringBuilder sb = new StringBuilder();
        for (String fruit : fruits) {
            sb.append(String.format("(%d) %s ",
                    count++, fruit));
        }
        printData(sb.toString());
    }
}
