package glacialExpedition.models.explorers;

import java.util.Map;

public class NaturalExplorer extends BaseExplorer {

    private static final double NATURAL_EXPLORER_ENERGY = 60;

    public NaturalExplorer(String name) {
        super(name, NATURAL_EXPLORER_ENERGY);
    }

    @Override
    public void search() {
        this.setEnergy(Math.max(0, this.getEnergy() - 7));
//        if (getEnergy() <= 7) {
//            this.setEnergy(0);
//        } else {
//            this.setEnergy(getEnergy() - 7);
//        }
    }
}
