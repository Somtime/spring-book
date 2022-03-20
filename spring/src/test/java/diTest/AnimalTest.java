package diTest;

import org.junit.jupiter.api.Test;
import toby.spring.spring.di.AnimalSound;
import toby.spring.spring.di.Cat;
import toby.spring.spring.di.Dog;

public class AnimalTest {
    @Test
    public void soundTest() {
        AnimalSound dog = new AnimalSound(new Dog());
        AnimalSound cat = new AnimalSound(new Cat());

        dog.sound();
        cat.sound();
    }
}
