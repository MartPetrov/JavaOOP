package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.Collection;

public class MissionImpl implements Mission {

    @Override
    public void explore(State state, Collection<Explorer> explorers) {
        //Итерираме изследователите
        //Итерираме експонатите на съответния щат

        Collection<String> stateExhibits = state.getExhibits();

        for (Explorer explorer : explorers) {
            while (explorer.canSearch() && stateExhibits.iterator().hasNext()) {
                explorer.search();
                String currentExhibits = stateExhibits.iterator().next();
                explorer.getSuitcase().getExhibits().add(currentExhibits);
                stateExhibits.remove(currentExhibits);
            }
        }
    }
}
