package threadlocal.chapter04atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 演示使用20个线程对AtomicLong进行10000次累加
 */
public class AtomicLongAdder {

    public static void main(String[] args) {
        AtomicLong counter = new AtomicLong();
        ExecutorService pool = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            pool.execute(new Task(counter));
        }
        pool.shutdown();
        while(!pool.isTerminated());
        long end = System.currentTimeMillis();
        System.out.printf("time used: %d ms\n", end - start);
        System.out.println(counter.get());
    }

    private static class Task implements Runnable{
        private AtomicLong counter;

        public Task(AtomicLong counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.incrementAndGet();
            }
        }
    }
}
