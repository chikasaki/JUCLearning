package threadlocal.chapter02threadlocal;

public class ThreadLocalTest {
    public static void main(String[] args) {
        TL.threadLocal.set(5);
        System.out.println(TL.threadLocal.get());
        TL.threadLocal.set(7);
        TL.threadLocal.set(8);
        TL.threadLocal.set(82);
        System.out.println(TL.threadLocal.get());
    }
}

class TL {
    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
}
