package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.players.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerRepository implements Repository{
    private Map<String, Player> models;

    public PlayerRepository() {
        this.models = new HashMap<>();
    }


    @Override
    public Collection getModels() {
        return this.models.values();
    }

    @Override
    public void add(Object model) {
        Player player = (Player) model;
        if (player == null) {
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_REPOSITORY);
        }

        models.put(player.getUsername(), player);
    }

    @Override
    public boolean remove(Object model) {
        Player player =(Player) model;
        boolean isRemoved = this.models.remove(player.getUsername(), player);

        return isRemoved;
    }

    @Override
    public Object findByName(String name) {
        if (!models.containsKey(name)) {
            return null;
        }

        return models.get(name);
    }
}
