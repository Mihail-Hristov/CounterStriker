package CounterStriker.core;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.common.OutputMessages;
import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepositories;
import CounterStriker.repositories.PlayerRepository;

import javax.swing.text.html.FormView;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller{
    private GunRepositories guns;
    private PlayerRepository players;
    private Field field;

    public ControllerImpl() {
        this.guns = new GunRepositories();
        this.players = new PlayerRepository();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun = null;

        switch (type) {
            case "Pistol":
                gun = new Pistol(name, bulletsCount);
                break;
            case "Rifle":
                gun = new Rifle(name, bulletsCount);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_GUN_TYPE);
        }

        guns.add(gun);

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_GUN, name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        Player player = null;

        switch (type) {
            case "Terrorist":
                Gun gunForTerrorist = (Gun) guns.findByName(gunName);
                if (gunForTerrorist == null) {
                    throw new NullPointerException(ExceptionMessages.GUN_CANNOT_BE_FOUND);
                }
                player = new Terrorist(username, health, armor, gunForTerrorist);

                break;
            case "CounterTerrorist":
                Gun gunForCounter = (Gun) guns.findByName(gunName);
                if (gunForCounter == null) {
                    throw new NullPointerException(ExceptionMessages.GUN_CANNOT_BE_FOUND);
                }
                player = new CounterTerrorist(username, health, armor, gunForCounter);

                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_TYPE);
        }

        players.add(player);

        return String.format(OutputMessages.SUCCESSFULLY_ADDED_PLAYER, username);
    }

    @Override
    public String startGame() {
        return this.field.start(players.getModels());
    }

    @Override
    public String report() {
        StringBuilder result = new StringBuilder();

        Collection<Object> returnedPlayers = players.getModels();

        List<Player> r = new ArrayList<>();

        for (Object o : returnedPlayers) {
            Player player = (Player) o;
            r.add(player);
        }

        List<Player> finalList = r.stream().sorted((p1, p2) -> {
            int one = p1.getClass().getSimpleName().compareTo(p2.getClass().getSimpleName());
            if (one == 0) {
                one = Integer.compare(p2.getHealth(), p1.getHealth());
            }
            if (one == 0) {
                one = p1.getUsername().compareTo(p2.getUsername());
            }
            return one;
        }).collect(Collectors.toList());

        for (Player player : finalList) {
            result.append(player.toString());
            result.append(System.lineSeparator());
        }

        return result.toString().trim();
    }
}
