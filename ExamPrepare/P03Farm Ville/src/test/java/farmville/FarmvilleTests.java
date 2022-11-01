package farmville;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class FarmvilleTests {

    private Animal animal1;
    private Animal animal2;
    private Farm farm1;
    private Farm farm2;

    @Before
    public void setup() {
        animal1 = new Animal("Dog", 100);
        animal2 = new Animal("Cat", 50);
        farm1 = new Farm("ferma1", 2);

    }

    @Test
    public void testConstructorGetAndSet() {
      Assert.assertEquals(farm1.getName(),"ferma1");
    }
    @Test(expected = NullPointerException.class)
    public void testConstructorSetInvalidName() {
        farm2 = new Farm(null, 5);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorSetInvalidCapacity() {
        farm2 = new Farm("ferma2", -1);
    }

    @Test
    public void testGetCount() {
        farm1.add(animal1);
        farm1.add(animal2);
        Assert.assertEquals(2,farm1.getCount());
    }

    @Test(expected =IllegalArgumentException.class)
    public void testAddWithExistsAnimal() {
        farm1.add(animal1);
        farm1.add(animal1);
    }

    @Test(expected =IllegalArgumentException.class)
    public void testAddWithFullCapacity() {
        farm1.add(animal1);
        farm1.add(animal2);
        farm1.add(new Animal("Parrot", 100));
    }

    @Test
    public void testRemoveSuccessful () {
        farm1.add(animal1);
        farm1.add(animal2);
        Assert.assertTrue(farm1.remove(animal1.getType()));
    }

}
