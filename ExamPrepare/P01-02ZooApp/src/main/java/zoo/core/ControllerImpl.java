package zoo.core;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;

import static zoo.common.ConstantMessages.*;
import static zoo.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private FoodRepositoryImpl foodRepository;
    private Collection<Area> areas;

    public ControllerImpl() {
        this.foodRepository = new FoodRepositoryImpl();
        this.areas = new ArrayList<>();
    }


    @Override
    public String addArea(String areaType, String areaName) {
        Area areaToAdd;
        switch (areaType) {
            case "WaterArea":
                areaToAdd = new WaterArea(areaName);
                break;
            case "LandArea":
                areaToAdd = new LandArea(areaName);
                break;
            default:
                throw new NullPointerException(INVALID_AREA_TYPE);
        }
        areas.add(areaToAdd);
        return String.format(SUCCESSFULLY_ADDED_AREA_TYPE, areaType);
    }

    @Override
    public String buyFood(String foodType) {
        Food food;
        switch (foodType) {
            case "Vegetable":
                food = new Vegetable();
                break;
            case "Meat":
                food = new Meat();
                break;
            default:
                throw new IllegalArgumentException(INVALID_FOOD_TYPE);
        }
        foodRepository.add(food);
        return String.format(SUCCESSFULLY_ADDED_FOOD_TYPE, foodType);
    }

    @Override
    public String foodForArea(String areaName, String foodType) {
        Area area = getAreaByName(areaName);
        Food food = foodRepository.findByType(foodType);
        if (food == null) {
            throw new IllegalArgumentException(String.format(NO_FOOD_FOUND, foodType));
        }
        area.addFood(food);
        return String.format(SUCCESSFULLY_ADDED_FOOD_IN_AREA, foodType, areaName);
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {
        Area area = getAreaByName(areaName);
        Animal animal;
        switch (animalType) {
            case "AquaticAnimal":
                animal = new AquaticAnimal(animalName, kind, price);
                break;
            case "TerrestrialAnimal":
                animal = new TerrestrialAnimal(animalName, kind, price);
                break;
            default:
                throw new IllegalArgumentException(INVALID_ANIMAL_TYPE);
        }
        if (area.getClass().getSimpleName().equals("WaterArea") && animalType.equals("AquaticAnimal")) {
            area.addAnimal(animal);
        } else if (area.getClass().getSimpleName().equals("LandArea") && animalType.equals("TerrestrialAnimal")) {
            area.addAnimal(animal);
        } else {
            return AREA_NOT_SUITABLE;
        }
        return String.format(SUCCESSFULLY_ADDED_ANIMAL_IN_AREA, animalType, areaName);
    }

    @Override
    public String feedAnimal(String areaName) {
        Area area = getAreaByName(areaName);
        for (Animal a : area.getAnimals()) {
            a.eat();
        }
        return String.format(ANIMALS_FED, area.getAnimals().size());
    }

    @Override
    public String calculateKg(String areaName) {
        Area area = getAreaByName(areaName);
        Collection<Animal> animals = area.getAnimals();
        double sumOfKg = animals.stream().mapToDouble(Animal::getKg).sum();
        return String.format(KILOGRAMS_AREA, areaName, sumOfKg);
    }

    @Override
    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        for (Area area : areas) {
            sb.append(area.getInfo());
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private Area getAreaByName(String areaName) {
        return areas.stream()
                .filter(a -> a.getName().equals(areaName))
                .findFirst()
                .orElse(null);
    }
}
