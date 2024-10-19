package org.example.thread.collection.java;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class SetMain {
    public static void main(String[] args) {
        Set<Integer> list = new CopyOnWriteArraySet<>();
        list.add(3);
        list.add(1);
        list.add(2);
        System.out.println("list = " + list);

        Set<Integer> skipSet = new ConcurrentSkipListSet<>();
        skipSet.add(3);
        skipSet.add(1);
        skipSet.add(2);
        System.out.println("skipSet = " + skipSet);
    }
}
