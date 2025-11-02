package app.utils;

import app.model.DataHandler;
import app.model.MyThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStarter {
    private static final String MESSAGE = "[Усі потоки готові (%d) — Погнали!]\n";

    public static void startThreads(DataHandler dataHandler, int amount) {
        List<MyThread<? extends DataHandler>> myThreads = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            myThreads.add(new MyThread<>("Thread " + i, dataHandler));
        }

        for (MyThread<? extends DataHandler> myThread : myThreads) {
            myThread.start();
        }
    }

    public static void startCyclicBarrier(DataHandler dataHandler, int amount) {
        CyclicBarrier barrier = new CyclicBarrier(amount, () -> System.out.printf(MESSAGE, amount));

        for (int i = 1; i <= amount; i++) {
            new MyThread<>("Thread " + i, () -> {
                try {
                    barrier.await();
                    dataHandler.getOutput();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    public static void startLatchChain(DataHandler dataHandler, int amount) {
        CountDownLatch[] latches = new CountDownLatch[amount + 1];
        for (int i = 0; i <= amount; i++) {
            latches[i] = new CountDownLatch(1);
        }
        latches[0].countDown();
        for (int i = 1; i <= amount; i++) {
            final int id = i;
            new MyThread<>("Thread " + id, () -> {
                try {
                    latches[id - 1].await();
                    dataHandler.getOutput();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latches[id].countDown();
                }
            }).start();
        }
    }

    public static void startTurnBasedOrder(DataHandler dataHandler, int amount) {
        ReentrantLock lock = new ReentrantLock();
        Condition turnChanged = lock.newCondition();
        AtomicInteger turn = new AtomicInteger(1);

        for (int i = 1; i <= amount; i++) {
            final int id = i;
            new MyThread<>("Thread " + id, () -> {
                lock.lock();
                try {
                    while (turn.get() != id) {
                        turnChanged.await();
                    }
                    dataHandler.getOutput();
                    turn.getAndAdd(1);
                    turnChanged.signalAll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            }).start();
        }
    }

    public static void startBarrierThenOrdered(DataHandler dataHandler, int amount) {
        CyclicBarrier barrier = new CyclicBarrier(amount, () -> System.out.printf(MESSAGE, amount));

        ReentrantLock lock = new ReentrantLock();
        Condition turnChanged = lock.newCondition();
        AtomicInteger turn = new AtomicInteger(1);

        for (int i = 1; i <= amount; i++) {
            final int id = i;
            new MyThread<>("Thread " + id, () -> {
                try {
                    barrier.await();

                    lock.lock();
                    try {
                        while (turn.get() != id) {
                            turnChanged.await();
                        }
                        dataHandler.getOutput();
                        turn.getAndAdd(1);
                        turnChanged.signalAll();
                    } finally {
                        lock.unlock();
                    }

                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

}
