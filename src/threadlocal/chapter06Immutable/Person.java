package threadlocal.chapter06Immutable;

public class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static String getName() {
        return "Person";
    }
}

class Man extends Person {

    public Man(String name, int age) {
        super(name, age);
    }

    public static String getName() {
        return "Man";
    }
}
