package threadlocal.chapter03lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockBarge {

    private static ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock(true);
    private static ReentrantReadWriteLock.ReadLock readLock = rwlock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = rwlock.writeLock();

    private static void read() {
        System.out.println(Thread.currentThread().getName() + "开始获取读锁");
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到读锁，准备读取");
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "读取完毕，释放读锁");
            readLock.unlock();
        }
    }

    private static void write() {
        System.out.println(Thread.currentThread().getName() + "开始获取写锁");
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "获取到写锁，准备写入");
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "写入完毕，释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> write(), "Thread1").start();
        new Thread(() -> read(), "Thread2").start();
        new Thread(() -> read(), "Thread3").start();
        new Thread(() -> write(), "Thread4").start();
        new Thread(() -> read(), "Thread5").start();
        new Thread(() -> {
            for(int i = 0; i < 1000; i ++) {
                new Thread(() -> read()).start();
            }
        }).start();
    }
}
