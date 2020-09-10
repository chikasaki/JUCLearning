package threadlocal.chapter02threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolSafe {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        HashSet<String> set = new HashSet<>();
        for(int i = 0; i < 1000; i ++) {
            int finalI = i;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    String str = parseDateToStr(finalI);
                    System.out.println(str);
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (ThreadPoolSafe.class) {
                        if(set.contains(str)){
                            System.out.println("repeat!");
                        }
                        set.add(str);
                    }
                }
            });
        }
    }

    private static String parseDateToStr(int mills) {
        Date date = new Date(mills*1000);
        String dateStr = null;
        dateStr = ThreadLocalFormatter.threadLocal.get().format(date);
        return dateStr;
    }
}

class ThreadLocalFormatter {
    public static ThreadLocal<SimpleDateFormat> threadLocal =
            ThreadLocal.withInitial(() ->
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            );

}
