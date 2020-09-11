package threadlocal.chapter04atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.stream.IntStream;

public class AccumulatorTest {
    public static void main(String[] args) {
        LongAccumulator accumulator = new LongAccumulator((x, y) -> x + y, 1);
        ExecutorService pool = Executors.newFixedThreadPool(20);
        IntStream.range(1, 1000).forEach(
                i -> pool.submit(() -> accumulator.accumulate(i))
        );
        pool.shutdown();
        while(!pool.isTerminated());
        System.out.println(accumulator.get());
    }
}
