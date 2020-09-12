package threadlocal.chapter09threadcontrol;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest implements Runnable{
    static Semaphore semaphore = new Semaphore(3, true);
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 100; i++) {
            pool.execute(new SemaphoreTest());
        }
        pool.shutdown();
    }

    @Override
    public void run() {
        try {
            semaphore.acquireUninterruptibly();
            semaphore.acquire();
            throw new RuntimeException();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
