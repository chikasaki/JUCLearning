package threadlocal.chapter03lock;

import java.util.concurrent.locks.ReentrantLock;

public class FairAndUnfair implements Runnable{

    //公平锁
//    private ReentrantLock lock = new ReentrantLock(true);
    //非公平锁
    private ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        FairAndUnfair task = new FairAndUnfair();
        Thread[] threads = new Thread[10];
        for(int i = 0; i < 10; i ++) {
            threads[i] = new Thread(task);
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始打印");
        lock.lock();
        try {
            int duration = (int) (Math.random() * 10 + 1);
            System.out.println(Thread.currentThread().getName() + "打印第一份, 需要" + duration + "s");
            Thread.sleep(duration*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

        lock.lock();
        try {
            int duration = (int) (Math.random() * 10 + 1);
            System.out.println(Thread.currentThread().getName() + "打印第二份, 需要" + duration + "s");
            Thread.sleep(duration*100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        System.out.println(Thread.currentThread().getName() + "打印结束");
    }
}
