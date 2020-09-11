package threadlocal.chapter07ConcurrentContainer;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest implements Runnable{

    private ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMapTest task = new ConcurrentHashMapTest();
        task.scores.put("ming", 0);
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(task.scores.get("ming"));
    }

    @Override
    public void run() {
        for(int i = 0; i < 10000; i ++) {
            boolean success;
            do {
                int old = scores.get("ming");
                int newS = old + 1;
                success = scores.replace("ming", old, newS);
            }while(!success);
        }
    }
}
