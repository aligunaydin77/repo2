
package com.gs.futures.refdata.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class VolatilePlayTest {

    private VolatilePlay volatilePlay;
    private List<Integer> collector;
    private Lock lock;


    @Before
    public void setUp() throws Exception {
        volatilePlay = new VolatilePlay();
        collector = new ArrayList<>();
        lock = new ReentrantLock();
    }

    @Test
    public void shouldMakeTheDataVisibleToAllThreads() throws Exception {
//
//        ExecutorService executor = Executors.newScheduledThreadPool(10);
//        executor.execute(this::hitCounter);
//        executor.awaitTermination(1, TimeUnit.HOURS);
//
        Thread thread1  = new Thread(null, this::hitCounter, "thread1");
        Thread thread2  = new Thread(null, this::hitCounter, "thread2");
        Thread thread3  = new Thread(null, this::hitCounter, "thread3");


        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    private void hitCounter() {
        while(true) {
            int nextValue = volatilePlay.getNext();
            System.out.println(Thread.currentThread().getName() + ":" + nextValue);
            lock.lock();
            if(collector.contains(nextValue)) {
                System.err.println("COLLISION DETECTED on " + nextValue + " within " + Thread.currentThread().getName());
                System.exit(0);
            }
            collector.add(nextValue);
            lock.unlock();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
