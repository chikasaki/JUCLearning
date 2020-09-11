package threadlocal.chapter06Immutable;

public class TestPerson {
    public static void main(String[] args) {
        final Person person = new Person("Alice", 18);
        System.out.println(person.name + ": " + person.age);
        person.name = "Bob";
        System.out.println(person.name + ": " + person.age);
        System.out.println(Man.getName());
    }
}
