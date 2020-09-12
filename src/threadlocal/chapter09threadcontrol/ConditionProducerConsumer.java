package threadlocal.chapter09threadcontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionProducerConsumer {

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static final int LEN = 10;
    private static List<Object> bag = new ArrayList<>(LEN);

    private static class Producer implements Runnable{

        @Override
        public void run() {
            while(true) {
                lock.lock();
                try {
                    if (bag.size() == LEN) {
                        condition.await();
                    }
                    Object o = new Object();
                    System.out.println("Producer produce " + o);
                    bag.add(o);
                    Thread.sleep(100);
                    condition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            while(true) {
                lock.lock();
                try {
                    if (bag.isEmpty()) {
                        condition.await();
                    }
                    Object o = bag.remove(0);
                    System.out.println("Consumer consume " + o);
                    Thread.sleep(200);
                    condition.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.start();
        Thread.sleep(100);
        thread2.start();
    }
}
