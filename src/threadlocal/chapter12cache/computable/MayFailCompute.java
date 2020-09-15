package threadlocal.chapter12cache.computable;

import java.io.IOException;

public class MayFailCompute implements Computable<String, Integer> {
    @Override
    public Integer compute(String arg) throws Exception{
        double random = Math.random();
        if(random < 0.5) throw new IOException("fail");
//        Thread.sleep(2000);
        System.out.println("success");
        return Integer.parseInt(arg);
    }
}
