package app.controller;

import app.model.DataHandlerAtomicCounter;
import app.model.DataHandlerRaceCondition;
import app.model.DataHandlerSynchronized;

import static app.utils.ThreadStarter.*;
import static app.view.AppModeView.getAppMode;

public class MainAppController {

    public static void start() {
        switch (getAppMode()) {
            case RACE_CONDITION -> raceCondition();
            case ATOMIC_RACE_CONDITION -> atomicCounter();
            case SYNCHRONIZED -> synchronize();
            case CYCLIC_BARRIER -> cyclicBarrier();
            case LATCH_CHAIN -> latchChain();
            case TURN_BASED_ORDER -> turnBasedOrder();
            case BARRIER_THEN_ORDERED -> barrierThenOrdered();
        }
    }

    private static void raceCondition() {
        DataHandlerRaceCondition dataHandler = new DataHandlerRaceCondition();
        startThreads(dataHandler, 6);
    }

    private static void atomicCounter() {
        DataHandlerAtomicCounter dataHandler = new DataHandlerAtomicCounter();
        startThreads(dataHandler, 6);
    }

    private static void synchronize() {
        DataHandlerSynchronized dataHandler = new DataHandlerSynchronized();
        startThreads(dataHandler, 6);
    }

    private static void cyclicBarrier() {
        DataHandlerSynchronized dataHandler = new DataHandlerSynchronized();
        startCyclicBarrier(dataHandler, 6);
    }

    public static void latchChain() {
        DataHandlerRaceCondition dataHandler = new DataHandlerRaceCondition();
        startLatchChain(dataHandler, 6);
    }

    public static void turnBasedOrder() {
        DataHandlerRaceCondition dataHandler = new DataHandlerRaceCondition();
        startTurnBasedOrder(dataHandler, 6);
    }

    public static void barrierThenOrdered() {
        DataHandlerRaceCondition dataHandler = new DataHandlerRaceCondition();
        startBarrierThenOrdered(dataHandler, 6);
    }

}
