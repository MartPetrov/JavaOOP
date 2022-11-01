package petStore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PetStoreTests {
    private PetStore petStore;
    private List<Animal> expectedList;
    private Animal animal1;
    private Animal animal2;
    private Animal animal3;

    @Before
    public void setUp() {
        petStore= new PetStore();
        animal1 = new Animal("Dog", 50,1500);
        animal2 = new Animal("Cat", 10,2000);
        animal3 = new Animal("Parrot", 2,100);
        expectedList = new ArrayList<>();
    }

    @Test
    public void testGetCount () {
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        Assert.assertEquals(2, petStore.getCount());
    }

    @Test
    public void testGetAnimals() {
        expectedList.add(animal1);
        expectedList.add(animal2);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        List<Animal> animals = petStore.getAnimals();
        Assert.assertEquals(expectedList, animals);
    }

    @Test
    public void testFindAllAnimalsWithMaxKg() {
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        expectedList.add(animal1);
        expectedList.add(animal2);
        expectedList = expectedList.stream().filter(c -> c.getMaxKilograms() > 100).collect(Collectors.toList());
        Assert.assertEquals(expectedList, petStore.findAllAnimalsWithMaxKilograms(100));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddInvalidAnimal () {
        Animal animal = null;
        petStore.addAnimal(animal);
    }

    @Test
    public void testGetTheMostExpensiveAnimal() {
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        petStore.addAnimal(animal3);
        Assert.assertEquals(animal2, petStore.getTheMostExpensiveAnimal());
    }

    @Test
    public void testFindAllAnimalBySpecie() {
        Animal animalDog = new Animal("Dog",140,400);
        petStore.addAnimal(animalDog);
        petStore.addAnimal(animal1);
        petStore.addAnimal(animal2);
        petStore.addAnimal(animal3);
        List<Animal> dog = petStore.findAllAnimalBySpecie("Dog");
        Assert.assertEquals(2,dog.size());
    }

    @Test
    public void testGetAndSetAge () {
        animal1.setAge(2);
        Assert.assertEquals(2,animal1.getAge());

    }
}