package CounterStriker.models.players;

import CounterStriker.common.ExceptionMessages;
import CounterStriker.models.guns.Gun;

public abstract class PlayerImpl implements Player{
    private String username;
    private int health;
    private int armor;
    private boolean isAlive;
    private Gun gun;

    protected PlayerImpl(String username, int health, int armor, Gun gun) {
        this.setUsername(username);
        this.setHealth(health);
        this.setArmor(armor);
        this.setGun(gun);
        isAlive = true;
    }

    private void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.INVALID_PLAYER_NAME);
        }

        this.username = username;
    }

    private void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_HEALTH);
        }

        this.health = health;
    }

    private void setArmor(int armor) {
        if (armor < 0) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_ARMOR);
        }

        this.armor = armor;
    }

    private void setGun(Gun gun) {
        if (gun == null) {
            throw new NullPointerException(ExceptionMessages.INVALID_GUN);
        }

        this.gun = gun;
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public int getArmor() {
        return this.armor;
    }

    @Override
    public Gun getGun() {
        return this.gun;
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public void takeDamage(int points) {
        int currentPoints = points;

        if (currentPoints <= this.getArmor()) {
            this.armor -= currentPoints;
        }else {
            currentPoints -= this.getArmor();
            this.armor = 0;
            if (currentPoints < this.getHealth()) {
                this.health -= currentPoints;
            } else {
                this.health = 0;
                this.isAlive = false;
            }
        }
    }

    @Override
    public String toString() {
        Gun gun = this.getGun();

        StringBuilder result = new StringBuilder();
        result.append(String.format("%s: %s", this.getClass().getSimpleName(), getUsername()));
        result.append(System.lineSeparator());
        result.append(String.format("--Health: %d", this.getHealth()));
        result.append(System.lineSeparator());
        result.append(String.format("--Armor: %d", this.getArmor()));
        result.append(System.lineSeparator());
        result.append(String.format("--Gun: %s", gun.getName()));

        return result.toString().trim();
    }
}
