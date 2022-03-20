package toby.spring.spring.di;

public class Dog implements Animal {
    @Override
    public void sound() {
        System.out.println("bark~!");
    }
}
