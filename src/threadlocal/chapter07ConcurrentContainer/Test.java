package threadlocal.chapter07ConcurrentContainer;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    public static void main(String[] args) {
        double sum = (double) Integer.MAX_VALUE + Integer.MAX_VALUE;
        System.out.println(sum);
    }
}
