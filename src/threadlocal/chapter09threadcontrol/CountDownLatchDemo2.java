package threadlocal.chapter09threadcontrol;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多等一，模拟100米跑步所有运动员等待裁判发出号令
 */
public class CountDownLatchDemo2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("运动员" + no + "准备完毕");
                    try {
                        count.await();
                        System.out.println("运动员" + no + "开跑");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            pool.execute(runnable);
        }
        Thread.sleep(5000);
        System.out.println("裁判发出号令");
        count.countDown();

        pool.shutdown();
        while(!pool.isTerminated());
    }
}
