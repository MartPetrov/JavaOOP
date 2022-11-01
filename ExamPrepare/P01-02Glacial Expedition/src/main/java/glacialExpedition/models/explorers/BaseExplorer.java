package glacialExpedition.models.explorers;

import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.suitcases.Carton;
import glacialExpedition.models.suitcases.Suitcase;


public abstract class BaseExplorer implements Explorer {
    private String name;
    private double energy;
    private Suitcase suitcase;

    protected BaseExplorer(String name, double energy) {
        setName(name);
        setEnergy(energy);
        this.suitcase = new Carton();
    }

    @Override
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.EXPLORER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    protected void setEnergy(double energy) {
        if (energy < 0) {
            throw new IllegalArgumentException(ExceptionMessages.EXPLORER_ENERGY_LESS_THAN_ZERO);
        }
        this.energy = energy;
    }

    @Override
    public Suitcase getSuitcase() {
        return suitcase;
    }

    protected void setSuitcase(Suitcase suitcase) {
        this.suitcase = suitcase;
    }

    @Override
    public boolean canSearch() {
        return this.energy > 0;
    }

    @Override
    public void search() {
        this.setEnergy(Math.max(0, this.getEnergy() - 15));
//        if (this.energy <= 15) {
//            this.energy = 0;
//        } else {
//            this.energy -= 15;
//        }
    }
}
