package threadlocal.chapter01threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) {
//        ExecutorService pool = Executors.newFixedThreadPool(5);
//        for (int i = 0; i < 1000; i++) {
//            pool.submit(new Task());
//        }

        ScheduledExecutorService pool = Executors.newScheduledThreadPool(10);
        pool.scheduleAtFixedRate(new Task(), 1000, 300, TimeUnit.MILLISECONDS);
    }
}

class Task implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
