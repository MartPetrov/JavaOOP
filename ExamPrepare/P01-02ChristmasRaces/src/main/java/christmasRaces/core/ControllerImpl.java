package christmasRaces.core;

import christmasRaces.common.ExceptionMessages;
import christmasRaces.common.OutputMessages;
import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.CarRepository;
import christmasRaces.repositories.DriverRepository;
import christmasRaces.repositories.RaceRepository;
import christmasRaces.repositories.interfaces.Repository;

import java.util.*;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private Repository<Driver> driverRepository;
    private Repository<Car> carRepository;
    private Repository<Race> raceRepository;


    public ControllerImpl(Repository<Driver> driverRepository, Repository<Car> carRepository, Repository<Race> raceRepository) {
        this.driverRepository = new DriverRepository();
        this.carRepository = new CarRepository();
        this.raceRepository = new RaceRepository();
    }

    @Override
    public String createDriver(String driverName) {
        Driver driver = new DriverImpl(driverName);
        if (driverRepository.getByName(driverName) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_EXISTS,driverName));
        }
        driverRepository.add(driver);
        return String.format(OutputMessages.DRIVER_CREATED,driverName);
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        Car car = type.equals("Muscle")
                ? new MuscleCar(model, horsePower)
                : new SportsCar(model, horsePower);
        if (driverRepository.getByName(car.getModel()) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS,model));
        }
        carRepository.add(car);
        return String.format(String.format("%sCar %s is created.",type,model));
    }

    @Override
    public String createRace(String name, int laps) {
        if (raceRepository.getByName(name) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, name));
        }
       Race race = new RaceImpl(name, laps);
        raceRepository.add(race);
        return String.format(OutputMessages.RACE_CREATED, name);
    }
    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Car car = carRepository.getByName(carModel);
        Driver driver = driverRepository.getByName(driverName);
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }
        if (car == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_NOT_FOUND, carModel));
        }
        driver.addCar(car);
        return String.format(OutputMessages.CAR_ADDED, driverName,carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        if (raceRepository.getByName(raceName) == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }
        Race currentRace = raceRepository.getByName(raceName);
        if (driverRepository.getByName(driverName) == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }
        Driver driverToAdd = driverRepository.getByName(driverName);
        currentRace.addDriver(driverToAdd);
        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        Race currentRace = raceRepository.getByName(raceName);
        if (currentRace == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }

        if (currentRace.getDrivers().stream().filter(Driver::getCanParticipate).count() < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID,raceName,3));
        }

        List<Driver> sortedDrivers = currentRace.getDrivers().stream().sorted((s1,s2) -> Double.compare
                (s2.getCar().calculateRacePoints(currentRace.getLaps()),
                s1.getCar().calculateRacePoints(currentRace.getLaps()))).limit(3)
                .collect(Collectors.toList());


        return String.format(OutputMessages.DRIVER_FIRST_POSITION,sortedDrivers.get(0).getName(), raceName) + System.lineSeparator() +
                String.format(OutputMessages.DRIVER_SECOND_POSITION,sortedDrivers.get(1).getName(),raceName) + System.lineSeparator() +
                String.format(OutputMessages.DRIVER_THIRD_POSITION,sortedDrivers.get(2).getName(),raceName);
    }

}
