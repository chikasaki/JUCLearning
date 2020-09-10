package threadlocal.chapter03lock;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {
    private AtomicReference<Thread> reference = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!reference.compareAndSet(null, current)) {
            System.out.println("try");
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        reference.compareAndSet(current, null);
    }

    public static void main(String[] args) {
        SpinLock lock = new SpinLock();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "尝试获取自旋锁");
                lock.lock();
                try{
                    System.out.println(Thread.currentThread().getName() + "获取到自旋锁");
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "释放自旋锁");
                    lock.unlock();
                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();
    }
}
