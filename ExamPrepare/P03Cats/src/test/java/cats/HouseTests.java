package cats;

import org.junit.Assert;
import org.junit.Test;

public class HouseTests {

    @Test(expected = IllegalArgumentException.class)
    public void testCreateHouseWithInvalidCapacity() {
         new House("House1", -4);

    }

    @Test(expected = NullPointerException.class)
    public void testCreateHouseWithInvalidName() {
        new House(null, 4);

    }
    @Test(expected = NullPointerException.class)
    public void testCreateHouseWithInvalidName2() {
         new House("", 4);
    }
    @Test
    public void testCreateHouse() {
        House house = new House("House1", 4);
        Assert.assertEquals("House1",house.getName());
        Assert.assertEquals(4,house.getCapacity());
    }

    @Test
    public void testAddCatSuccessful() {
        House house = new House("House", 10);
        Cat cat = new Cat("Mike");
        Assert.assertEquals(0, house.getCount());
        house.addCat(cat);
        Assert.assertEquals(1, house.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testAddCatFail() {
        House house = new House("House", 1);
        Cat cat1 = new Cat("Mike");
        Cat cat2 = new Cat("Tuby");
        house.addCat(cat1);
        house.addCat(cat2);
    }

    @Test
    public void testRemoveCatSuccessful() {
        House house = new House("House", 10);
        Cat cat = new Cat("Mike");
        house.addCat(cat);
        Assert.assertEquals(1, house.getCount());
        house.removeCat("Mike");
        Assert.assertEquals(0, house.getCount());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveCatFail() {
        House house = new House("House", 10);
        Cat cat = new Cat("Mike");
        house.addCat(cat);
        house.removeCat("Tuby");
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCatForSaleCatFail() {
        House house = new House("House", 10);
        Cat cat = new Cat("Mike");
        house.addCat(cat);
        house.catForSale("Tuby");
    }
    @Test
    public void testCatForSale() {
        House house = new House("House", 10);
        Cat cat = new Cat("Mike");
        house.addCat(cat);
        Cat mike = house.catForSale("Mike");
        Assert.assertFalse(mike.isHungry());
    }


    @Test
    public void testStatistics () {
        House house = new House("House", 10);
        Cat cat1 = new Cat("Mike");
        Cat cat2 = new Cat("Tuby");
        Cat cat3 = new Cat("Betty");
        house.addCat(cat1);
        house.addCat(cat2);
        house.addCat(cat3);
        Assert.assertEquals("The cat Mike, Tuby, Betty is in the house House!",house.statistics());

    }
}
