package threadlocal.chapter09threadcontrol;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 五个任务全部完成主线程才能继续执行
 */
public class CountDownLatchDemo1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(5);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    System.out.println("工人" + no + "开始检查");
                    try {
                        Thread.sleep((long)(Math.random()*5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println("工人" + no + "检查完毕");
                        count.countDown();
                    }
                }
            };
            pool.execute(task);
        }

        System.out.println("主线程开始等待");
        count.await();
        System.out.println("任务完成，进入下一个阶段");
    }
}
