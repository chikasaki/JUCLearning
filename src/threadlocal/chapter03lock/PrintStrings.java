package threadlocal.chapter03lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintStrings {

    private static Lock lock = new ReentrantLock();

    private static void print(String str) {
        while(true) {
            lock.lock();
            try {
                for (int i = 0; i < str.length(); i++) {
                    System.out.print(str.charAt(i));
                }
                System.out.println();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            print("悟空");
        });
        Thread thread2 = new Thread(() -> {
            print("大师兄");
        });

        thread1.start();
        thread2.start();
    }
}
