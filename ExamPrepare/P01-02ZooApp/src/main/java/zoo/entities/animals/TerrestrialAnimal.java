package zoo.entities.animals;

public class TerrestrialAnimal extends BaseAnimal{
    private static final double DEFAULT_KG_Terrestrial = 5.50;

    public TerrestrialAnimal(String name, String kind, double price) {
        super(name, kind, DEFAULT_KG_Terrestrial, price);
    }

    @Override
    public void eat() {
        this.setKg(this.getKg() + 5.70);
    }
}
