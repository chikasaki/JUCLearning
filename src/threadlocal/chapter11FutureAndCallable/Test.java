package threadlocal.chapter11FutureAndCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {


    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        Callable<Integer> c1 = () -> {
            Thread.sleep(2000);
            return new Random().nextInt();
        };
        Callable<Integer> c2 = () -> {
            Thread.sleep(1000);
            return new Random().nextInt();
        };

        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if(i < 10) {
                list.add(pool.submit(c2));
            } else {
                list.add(pool.submit(c1));
            }
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            Integer k = null;
            try {
                k = list.get(i).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println(k);
            if(i == 9 || i == 19) {
                long end = System.currentTimeMillis();
                System.out.println(end - start);
            }
        }
    }
}
