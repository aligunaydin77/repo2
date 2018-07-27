package com.gs.futures.refdata.services.prime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.zip.GZIPInputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ThrottlingTest {

    private static final String MESSAGE = "hello";
    public static final int REQUEST_PAUSE = 1000;
    public static final int NUMBER_OF_REQUESTS = 5;
    Throttling throttling = new Throttling(3, 5000);

    @Mock
    Consumer<String> mockConsumer;

    @Test
    public void shouldProcessFairNumberOfFunctions() throws Exception {
        Thread thread = new Thread(() ->
                IntStream.rangeClosed(1, NUMBER_OF_REQUESTS).forEach(integer -> {
                    throttling.process(mockConsumer, MESSAGE);
                    try {
                        Thread.sleep(REQUEST_PAUSE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }));
        thread.start();
        Thread.sleep(REQUEST_PAUSE * NUMBER_OF_REQUESTS);
        verify(mockConsumer, times(3)).accept(MESSAGE);
        thread.join();
        verify(mockConsumer, times(5)).accept(MESSAGE);
    }

    @Test
    public void useDecoratorPattern() throws Exception {
        FileInputStream in = new FileInputStream("boo");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new GZIPInputStream(new FileInputStream("boo")));
    }

    @Test
    public void useStrategyPattern() throws Exception {
        List<Item> list = new ArrayList<>();
        list.add(new Item("boo"));
        list.add(new Item("aoo"));
        list.add(new Item("coo"));

        Collections.sort(list, Comparator.comparing(Item::getWhat));

        list.forEach(System.out::println);
    }

    class Item {
        private String what;

        public Item(String what) {
            this.what = what;
        }

        public String getWhat() {
            return what;
        }

        @Override
        public String toString() {
            return "{\"what\":}" + what;
        }
    }

}
