package threadlocal.chapter12cache;

import threadlocal.chapter12cache.computable.Computable;
import threadlocal.chapter12cache.computable.MayFailCompute;
import threadlocal.chapter12cache.computable.TestCompute;

import java.util.concurrent.*;

/**
 * 不会重复计算的版本
 */
public class Cache2 {
    private static ConcurrentHashMap<String, Future<Integer>> cache = new ConcurrentHashMap<>();
    private Computable<String, Integer> computable;

    public Cache2(Computable<String, Integer> computable) {
        this.computable = computable;
    }

    public Integer compute(String arg){
        while(true) {
            Future<Integer> future = cache.get(arg);
            if (future == null) {
                Callable<Integer> callable = () -> computable.compute(arg);
                FutureTask<Integer> futureTask = new FutureTask<>(callable);
                future = cache.putIfAbsent(arg, futureTask);
                if (future == null) {
                    future = futureTask;
                    System.out.println("缓存未找到目标，开始计算");
                    futureTask.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {
                System.out.println("be cancelled");
                remove(future, arg);
                return null;
            } catch (InterruptedException e) {
                System.out.println("be Interrupted");
                remove(future, arg);
                return null;
            } catch (ExecutionException e) {
                System.out.println(e.getMessage());
                remove(future, arg);
            }
        }
    }

    private synchronized void remove(Future<Integer> future, String arg) {
        Future<Integer> ano = cache.get(arg);
        if (ano == future) {
            cache.remove(arg);
        }
    }

    public static void main(String[] args) {
//        Computable<String, Integer> computable = new TestCompute();
        Computable<String, Integer> computable = new MayFailCompute();
        Cache2 cache1 = new Cache2(computable);

        ExecutorService pool = Executors.newFixedThreadPool(5);
        Runnable runnable1 = () -> System.out.println(cache1.compute("666"));
        Runnable runnable2 = () -> System.out.println(cache1.compute("667"));
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            if(i < 50) pool.execute(runnable1);
            else pool.execute(runnable2);
        }
        pool.shutdown();
        while(!pool.isTerminated());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
