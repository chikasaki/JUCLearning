package threadlocal.chapter03lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BookTickets implements Runnable{
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        BookTickets task = new BookTickets();
        for(int i = 0; i < 5; i ++) {
            Thread thread = new Thread(task, "person" + i);
            thread.start();
        }
    }


    @Override
    public void run() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始预定票");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "预定完成, 释放锁");
            lock.unlock();
        }
    }
}
