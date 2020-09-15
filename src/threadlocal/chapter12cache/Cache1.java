package threadlocal.chapter12cache;

import threadlocal.chapter12cache.computable.Computable;
import threadlocal.chapter12cache.computable.TestCompute;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 可能产生重复计算的版本
 */
public class Cache1 {
    private static ConcurrentHashMap<String, Integer> cache = new ConcurrentHashMap<>();
    private Computable<String, Integer> computable;

    public Cache1(Computable<String, Integer> computable) {
        this.computable = computable;
    }

    public Integer compute(String arg) {
        Integer res = cache.get(arg);
        if(res == null) {
            System.out.println("缓存未找到目标，开始计算");
            try {
                res = computable.compute(arg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cache.putIfAbsent(arg, res);
        }
        return res;
    }

    public static void main(String[] args) {
        Computable<String, Integer> computable = new TestCompute();
        Cache1 cache1 = new Cache1(computable);

        ExecutorService pool = Executors.newFixedThreadPool(5);
        Runnable runnable = () -> {
            System.out.println(cache1.compute("666"));
        };
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            pool.execute(runnable);
        }
        pool.shutdown();
        while(!pool.isTerminated());
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
