package garage;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GarageTests {
    private Garage garage;
    private Car car1;
    private Car car2;
    private List<Car> carListExpected;


    @Before
    public void setUp() {
        garage = new Garage();
        car1 = new Car("Mercedes", 210, 40000);
        car2 = new Car("Skoda", 220, 30000);
        carListExpected = new ArrayList<>();
    }

    @Test
    public void testGetCars() {
        garage.addCar(car1);
        garage.addCar(car2);
        carListExpected.add(car1);
        carListExpected.add(car2);
        Assert.assertEquals(carListExpected, garage.getCars());
    }

    @Test
    public void testGetCount() {
        garage.addCar(car1);
        garage.addCar(car2);
        Assert.assertEquals(2, garage.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCarNull() {
        Car car = null;
        garage.addCar(car);
    }

    @Test
    public void testGetTheMostExpensiveCar() {
        garage.addCar(car1);
        garage.addCar(car2);
        garage.addCar(new Car("Opel", 160, 50000));
        Car maxPriceCarFromMethod = garage.getTheMostExpensiveCar();
        Assert.assertEquals(maxPriceCarFromMethod.getBrand(), "Opel");
    }


    @Test
    public void testFindAllCarsByBrand() {
        garage.addCar(new Car("Opel", 160, 50000));
        garage.addCar(new Car("Opel", 180, 40000));
        garage.addCar(new Car("Opel", 190, 60000));
        garage.addCar(car1);
        garage.addCar(car2);
        carListExpected.add(new Car("Opel", 160, 50000));
        carListExpected.add(new Car("Opel", 180, 40000));
        carListExpected.add(new Car("Opel", 190, 60000));
        Assert.assertEquals(carListExpected.size(), garage.findAllCarsByBrand("Opel").size());
    }

    @Test
    public void testFindAllCarsWithMaxSpeedAbove() {
        garage.addCar(new Car("Opel", 160, 50000));
        garage.addCar(new Car("Opel", 180, 40000));
        garage.addCar(new Car("Opel", 190, 60000));
        garage.addCar(car1);
        garage.addCar(car2);
        carListExpected.add(car1);
        carListExpected.add(car2);
        Assert.assertEquals(carListExpected.size(), garage.findAllCarsWithMaxSpeedAbove(200).size());
    }
}