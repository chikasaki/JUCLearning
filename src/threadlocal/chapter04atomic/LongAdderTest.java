package threadlocal.chapter04atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 演示使用20个线程对LongAdder进行10000次累加
 */
public class LongAdderTest {

    public static void main(String[] args) {
        LongAdder counter = new LongAdder();
        ExecutorService pool = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            pool.execute(new Task(counter));
        }
        pool.shutdown();
        while(!pool.isTerminated());
        long end = System.currentTimeMillis();
        System.out.printf("time used: %d ms\n", end - start);
        System.out.println(counter.sum());
    }

    private static class Task implements Runnable{
        private LongAdder counter;

        public Task(LongAdder counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        }
    }
}
