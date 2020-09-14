package threadlocal.chapter11FutureAndCallable;

import java.util.concurrent.*;

public class CancelAndGet {

    public static void main(String[] args) {
        Callable<String> callable = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "default";
            }
            return "最新广告";
        };

        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<String> future = pool.submit(callable);
        future.cancel(false);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        try {
//            future.get(1000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            System.out.println("超时");
//            future.cancel(false);
//            try {
//                String s = future.get();
//                System.out.println(s);
//            } catch (InterruptedException interruptedException) {
//                interruptedException.printStackTrace();
//            } catch (ExecutionException executionException) {
//                executionException.printStackTrace();
//            }
//
//        }
    }
}
