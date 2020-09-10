package threadlocal.chapter01threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTerminate {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            pool.execute(new TerminateTask());
        }
        Thread.sleep(1500);
        List<Runnable> runnables = pool.shutdownNow();
        System.out.println(runnables.size());
        System.out.println(pool.isTerminated());
    }
}

class TerminateTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "被终止了");
        }

    }
}
