package zoo.entities.areas;

import zoo.entities.animals.Animal;
import zoo.entities.foods.Food;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static zoo.common.ExceptionMessages.AREA_NAME_NULL_OR_EMPTY;
import static zoo.common.ExceptionMessages.NOT_ENOUGH_CAPACITY;

public abstract class BaseArea implements Area {
    private String name;
    private int capacity;
    private Collection<Food> foods;
    private Collection<Animal> animals;

    public BaseArea(String name, int capacity) {
        this.setName(name);
        this.setCapacity(capacity);
        this.foods = new ArrayList<>();
        this.animals = new ArrayList<>();
    }

    private void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(AREA_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<Animal> getAnimals() {
        return this.animals;
    }

    @Override
    public Collection<Food> getFoods() {
        return this.foods;
    }

    @Override
    public int sumCalories() {
        return this.foods.stream().mapToInt(Food::getCalories).sum();
    }

    @Override
    public void addAnimal(Animal animal) {
        if (animals.size() >= this.capacity) {
            throw new IllegalStateException(NOT_ENOUGH_CAPACITY);
        }
        animals.add(animal);
    }

    @Override
    public void removeAnimal(Animal animal) {
        this.animals.remove(animal);
    }

    @Override
    public void addFood(Food food) {
        this.foods.add(food);
    }

    @Override
    public void feed() {
        for (Animal animal : animals) {
            animal.eat();
        }
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder();
        List<Animal> listOfAnimals = new ArrayList<>(animals);
        sb.append(String.format("%s (%s):", this.name, this.getClass().getSimpleName()));
        sb.append(System.lineSeparator());
        sb.append("Animals: ");
        if (animals.isEmpty()) {
            sb.append("none");
        } else {
            for (int index = 0; index <= listOfAnimals.size() - 1; index++) {
                if (index != listOfAnimals.size() - 1) {
                    sb.append(listOfAnimals.get(index).getName()).append(" ");
                } else {
                    sb.append(listOfAnimals.get(index).getName());
                }
            }

        }
        sb.append(System.lineSeparator());
        sb.append("Foods: ").append(foods.size());
        sb.append(System.lineSeparator());
        sb.append("Calories: ").append(sumCalories());
        sb.append(System.lineSeparator());
        return sb.toString().trim();
    }
}

