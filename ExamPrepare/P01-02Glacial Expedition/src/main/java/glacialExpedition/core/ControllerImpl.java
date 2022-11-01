package glacialExpedition.core;

import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.Repository;
import glacialExpedition.repositories.StateRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static glacialExpedition.common.ConstantMessages.*;
import static glacialExpedition.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Explorer> explorerRepository;
    private Repository<State> stateRepository;
    private int exploredStates;


    public ControllerImpl() {
        this.explorerRepository = new ExplorerRepository();
        this.stateRepository = new StateRepository();
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer = null;
        switch (type) {
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(EXPLORER_INVALID_TYPE);
        }
        explorerRepository.add(explorer);
        return String.format(EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        Collection<String> stateExhibits = state.getExhibits();
        Collections.addAll(stateExhibits, exhibits);
        this.stateRepository.add(state);
        return String.format(STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer currentExplorer = this.explorerRepository.byName(explorerName);
        if (currentExplorer == null) {
            throw new IllegalArgumentException(String.format(EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        explorerRepository.remove(currentExplorer);
        return String.format(EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        //Дали имаме изследователи с повече от 50 енергия/
        List<Explorer> explorers = explorerRepository
                .getCollection().stream()
                .filter(ex -> ex.getEnergy() > 50)
                .collect(Collectors.toList());
        //Ако нямаме хвърляме грешка
        if (explorers.isEmpty()) {
            throw new IllegalArgumentException(STATE_EXPLORERS_DOES_NOT_EXISTS);
        }
        //Ако имаме започваме мисията
        State stateToExplore = this.stateRepository.byName(stateName);
        Mission mission = new MissionImpl();
        mission.explore(stateToExplore, explorers);

        //Да върнем колко изследователи са се пенсионирали
        this.exploredStates++;
        long retired = explorers.stream().filter(e -> e.getEnergy() == 0).count();
        return String.format(STATE_EXPLORER, stateName, retired);
    }

    @Override
    public String finalResult() {
        StringBuilder result = new StringBuilder();
        result.append(String.format(FINAL_STATE_EXPLORED, exploredStates)).append(System.lineSeparator());
        result.append(FINAL_EXPLORER_INFO).append(System.lineSeparator());
        Collection<Explorer> explorers = this.explorerRepository.getCollection();

        for (Explorer explorer : explorers) {
            result.append(String.format(FINAL_EXPLORER_NAME, explorer.getName())).append(System.lineSeparator());
            result.append(String.format(FINAL_EXPLORER_ENERGY, explorer.getEnergy())).append(System.lineSeparator());
            if (explorer.getSuitcase().getExhibits().isEmpty()) {
                result.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS, "None")).append(System.lineSeparator());
            } else {
                result.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS,
                        String.join(FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER, explorer.getSuitcase().getExhibits())))
                        .append(System.lineSeparator());
            }
        }
        return result.toString().trim();
    }
}
