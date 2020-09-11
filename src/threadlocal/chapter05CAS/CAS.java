package threadlocal.chapter05CAS;

import java.util.concurrent.atomic.AtomicInteger;

public class CAS {
    private volatile int value;

    public int compareAndSwap(int except, int newValue) {
        int oldValue = value;
        if(oldValue == except) {
            value = newValue;
        }
        return oldValue;
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
    }
}
