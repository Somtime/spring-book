package toby.spring.spring.di;

public class Cat implements Animal {
    @Override
    public void sound() {
        System.out.println("meow~!");
    }
}
