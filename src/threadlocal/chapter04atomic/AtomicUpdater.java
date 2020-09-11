package threadlocal.chapter04atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicUpdater implements Runnable{

    private Candidate peter = new Candidate();
    private Candidate tom = new Candidate();

    private AtomicIntegerFieldUpdater<Candidate> updater = AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    @Override
    public void run() {
        for(int i = 0; i < 100000; i ++) {
            peter.score ++;
            updater.getAndIncrement(tom);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicUpdater task = new AtomicUpdater();
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(task.peter.score);
        System.out.println(task.tom.score);
    }

    class Candidate {
        volatile int score;
    }
}
