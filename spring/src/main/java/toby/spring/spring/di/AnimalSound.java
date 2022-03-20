package toby.spring.spring.di;

public class AnimalSound {
    private Animal animal;

    public AnimalSound(Animal animal) {
        this.animal = animal;
    }

    public void sound() {
        animal.sound();
    }
}
