package CounterStriker.models.players;

import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.GunImpl;

public class Terrorist extends PlayerImpl {

    public Terrorist(String username, int health, int armor, Gun gun) {
        super(username, health, armor, gun);
    }
}
