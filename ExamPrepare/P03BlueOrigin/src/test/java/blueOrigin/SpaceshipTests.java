package blueOrigin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpaceshipTests {
    private Spaceship spaceship;
    private Astronaut astronaut1;
    private Astronaut astronaut2;
    private Astronaut astronaut3;
    @Before
    public void setUp() {
        this.spaceship = new Spaceship("Softuni Professionals", 250);
        this.astronaut1 = new Astronaut("Pesho", 99);
        this.astronaut2 = new Astronaut("Ivan", 100);
        this.astronaut3 = new Astronaut("Martin", 99);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetCapacityShouldFailWithNegativeNumber () {
        new Spaceship("Softuni Professionals", -12);

    }

    @Test (expected =  NullPointerException.class)
        public void testSetNameShouldFailWithEmptyString () {
        new Spaceship("         ", 12);
    }

    @Test
    public void testCreateSpaceShip () {
        new Spaceship("Softuni Professionals", 12);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddShouldFailWithoutCapacity() {
       Spaceship spaceship1 =  new Spaceship("Softuni Professionals", 1);
        spaceship1.add(astronaut1);
        spaceship1.add(astronaut2);

    }
    @Test (expected = IllegalArgumentException.class)
    public void testAddShouldFailWithSameAstr() {
        Spaceship spaceship1 =  new Spaceship("Softuni Professionals", 2);
        spaceship1.add(astronaut1);
        spaceship1.add(astronaut1);

    }

    @Test
    public void testAddShouldWork() {
        Spaceship spaceship1 =  new Spaceship("Softuni Professionals", 5);
        spaceship1.add(astronaut1);
        Assert.assertEquals(1,spaceship1.getCount());
        spaceship1.add(astronaut2);
        Assert.assertEquals(2,spaceship1.getCount());
        spaceship1.add(astronaut3);
        Assert.assertEquals(3,spaceship1.getCount());
        Assert.assertEquals("Softuni Professionals",spaceship1.getName());

    }
    @Test
    public void testRemoveShouldRemove() {
        this.spaceship.add(astronaut1);
        Assert.assertEquals(1,spaceship.getCount());
        this.spaceship.remove(astronaut1.getName());
        Assert.assertEquals(0,spaceship.getCount());
    }

    @Test
    public void testRemoveShouldNotRemove() {
        this.spaceship.add(astronaut1);
        Assert.assertEquals(1,spaceship.getCount());
        this.spaceship.remove("Nakov");
        Assert.assertEquals(1,spaceship.getCount());
    }

}
