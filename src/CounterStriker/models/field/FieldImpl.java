package CounterStriker.models.field;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FieldImpl implements Field{

    @Override
    public String start(Collection<Player> players) {
        List<Player> terrorist = new ArrayList<>();
        List<Player> counter = new ArrayList<>();

        String result = "";
        boolean allInTeamIsDead = false;

        for (Player player : players) {
            if (player.getClass().getSimpleName().equals("Terrorist")) {
                terrorist.add(player);
            }else {
                counter.add(player);
            }
        }
        Collections.reverse(terrorist);

        int round = 1;
        while (!allInTeamIsDead) {

            if (round % 2 != 0) {
                for (int i = 0; i < terrorist.size(); i++) {
                    for (int j = 0; j < counter.size(); j++) {
                        int currentDamage = terrorist.get(i).getGun().fire();
                        Player currentPlayer = counter.get(j);
                        currentPlayer.takeDamage(currentDamage);
                        if (!currentPlayer.isAlive()) {
                            counter.remove(currentPlayer);
                        }
                    }
                }
            }else {
                for (int i = 0; i < counter.size(); i++) {
                    for (int j = 0; j < terrorist.size(); j++) {
                        int currentDamage = counter.get(i).getGun().fire();
                        Player currentPlayer = terrorist.get(j);
                        currentPlayer.takeDamage(currentDamage);
                        if (!currentPlayer.isAlive()) {
                            terrorist.remove(currentPlayer);
                        }
                    }
                }
            }

            if (terrorist.isEmpty()) {
                result = OutputMessages.COUNTER_TERRORIST_WINS;
                allInTeamIsDead = true;
            }else if (counter.isEmpty()) {
                result = OutputMessages.TERRORIST_WINS;
                allInTeamIsDead = true;
            }

            round ++;
        }


        return result;
    }
}
