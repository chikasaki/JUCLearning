package threadlocal.chapter09threadcontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(10);
        System.out.println(list.size());

    }
}
