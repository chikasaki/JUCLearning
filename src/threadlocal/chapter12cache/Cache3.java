package threadlocal.chapter12cache;

import threadlocal.chapter12cache.computable.Computable;
import threadlocal.chapter12cache.computable.MayFailCompute;
import threadlocal.chapter12cache.computable.TestCompute;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 缓存过期，时间随机避免缓存穿透
 */
public class Cache3 {
    private static ConcurrentHashMap<String, Future<Integer>> cache = new ConcurrentHashMap<>();
    private Computable<String, Integer> computable;
    private static ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(5);

    public Cache3(Computable<String, Integer> computable) {
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

    public Integer computeWithTimeout(String arg, long time, TimeUnit unit) {
        scheduledPool.schedule(() -> deleteTask(arg), time, unit);
        return compute(arg);
    }

    private synchronized void deleteTask(String arg) {
        Future<Integer> future = cache.get(arg);
        if(future != null) {
            if(!future.isDone()) {
                System.out.println("任务取消");
                future.cancel(true);
            }

            System.out.println("取消缓存");
            cache.remove(arg);
        }
    }

    private synchronized void remove(Future<Integer> future, String arg) {
        Future<Integer> ano = cache.get(arg);
        if (ano == future) {
            cache.remove(arg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        Computable<String, Integer> computable = new TestCompute();
        Cache3 cache3 = new Cache3(computable);
        ExecutorService pool = Executors.newFixedThreadPool(100);
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "准备开启任务");
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "    " + ThreadLocalUtil.dflocal.get().format(new Date()));
            System.out.println(cache3.compute("666"));
            System.out.println(Thread.currentThread().getName() + "结束任务");
        };
        for (int i = 0; i < 100; i++) {
            pool.submit(runnable);
        }
        Thread.sleep(2000);
        count.countDown();

        pool.shutdown();
    }

}

class ThreadLocalUtil {
    public static ThreadLocal<SimpleDateFormat> dflocal = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("mm:ss")
    );
}
