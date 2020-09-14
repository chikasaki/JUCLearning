package threadlocal.chapter11FutureAndCallable;

import java.util.Random;
import java.util.concurrent.*;

public class FutureTaskUsing {
    public static void main(String[] args) {
        Task task = new Task();
        FutureTask<Integer> futureTask = new FutureTask<>(task);
//        new Thread(futureTask).start();
        ExecutorService pool = Executors.newFixedThreadPool(2);
        pool.submit(futureTask);
        pool.shutdown();

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return sum(100);
        }

        private int sum(int k) {
            if(k == 1) return k;
            return sum(k - 1) + k;
        }
    }
}
