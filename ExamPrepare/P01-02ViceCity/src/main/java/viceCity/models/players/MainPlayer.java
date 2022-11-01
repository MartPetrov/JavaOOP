package viceCity.models.players;

import viceCity.models.guns.Gun;
import viceCity.repositories.interfaces.Repository;

public class MainPlayer extends BasePlayer{
        private static final String DEFAULT_NAME = "Tommy Vercetti";
        private static final int TOMMY_START_HEALTH = 100;

    public MainPlayer() {
        super(DEFAULT_NAME, TOMMY_START_HEALTH);
    }
}
