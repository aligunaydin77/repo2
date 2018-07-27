package com.gs.futures.refdata.services;

public class VolatilePlay {
    private  int counter = 0;
    public synchronized int getNext() {
        return counter++;
    }
}

