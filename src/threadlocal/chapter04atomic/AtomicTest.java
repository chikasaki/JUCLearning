package threadlocal.chapter04atomic;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicTest implements Runnable{
    private AtomicIntegerArray array = new AtomicIntegerArray(20);

    public static void main(String[] args) throws InterruptedException {
        AtomicTest task = new AtomicTest();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }

        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        for(int i = 0; i < task.array.length(); i ++) {
            System.out.println(task.array.get(i));
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            for(int j = 0; j < array.length(); j ++) {
                array.incrementAndGet(j);
            }
        }
    }
}
