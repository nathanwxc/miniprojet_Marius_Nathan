package entities;
import utils.DisplayUtils;

public abstract class Entity {
    protected String name;
    protected int hp;
    protected int attack;
    protected int defense;

    public Entity(String name, int hp, int attack, int defense) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
    }

    public boolean isAlive() {
        return hp > 0;
    }
    public void receiveHealing(int amount) {
        this.hp += amount;
    }

    public void receiveDamage(int damage) {
        int effectiveDamage = Math.max(0, damage - defense);
        hp -= effectiveDamage;
        DisplayUtils.display(name + " reçoit " + effectiveDamage + " points de dégâts. (HP: " + hp + ")");
    }

    public int getAttack() {
        return attack;
    }

    public String getName() {
        return name;
    }
}