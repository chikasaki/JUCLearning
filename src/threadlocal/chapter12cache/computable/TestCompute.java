package threadlocal.chapter12cache.computable;

public class TestCompute implements Computable<String, Integer>{
    @Override
    public Integer compute(String arg) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(arg);
    }
}
