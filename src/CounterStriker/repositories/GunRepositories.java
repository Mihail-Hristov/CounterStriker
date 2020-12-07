package CounterStriker.repositories;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;

import java.util.*;

public class GunRepositories implements Repository{
    private Map<String, Object> models;

    public GunRepositories() {
        this.models = new HashMap<>();
    }

    @Override
    public Collection getModels() {
        return this.models.values();
    }

    @Override
    public void add(Object model) {
        Gun gun = (Gun) model;
        if (gun == null) {
            throw new NullPointerException(ExceptionMessages.INVALID_GUN_REPOSITORY);
        }

        models.put(gun.getName(), gun);
    }

    @Override
    public boolean remove(Object model) {
        Gun gun = (Gun) model;
        boolean isRemoved = this.models.remove(gun.getName(), gun);

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
