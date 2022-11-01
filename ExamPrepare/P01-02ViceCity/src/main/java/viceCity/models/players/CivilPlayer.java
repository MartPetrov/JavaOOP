package viceCity.models.players;

public class CivilPlayer extends BasePlayer{

    private static final int DEFAULT_START_HEALTH = 50;

    public CivilPlayer(String name) {
        super(name, DEFAULT_START_HEALTH);
    }
}
