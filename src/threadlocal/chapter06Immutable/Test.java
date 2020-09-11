package threadlocal.chapter06Immutable;

public class Test {
    public static void main(String[] args) {
        String a = "wukong2";
        final String b = "wukong";
        String d = "wukong";
        String c = b + 2;
        String e = d + 2;
        System.out.println(a == c);
        System.out.println(a == e);
    }

    private static String getStr() {
        return "wukong";
    }
}
