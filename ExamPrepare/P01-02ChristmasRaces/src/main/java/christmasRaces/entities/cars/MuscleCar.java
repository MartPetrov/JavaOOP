package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public class MuscleCar extends BaseCar{
    private static final double CUBIC_CENTIMETERS_MUSCLE_CAR = 5000;

    public MuscleCar(String model, int horsePower) {
        super(model, horsePower, CUBIC_CENTIMETERS_MUSCLE_CAR);
    }

}
