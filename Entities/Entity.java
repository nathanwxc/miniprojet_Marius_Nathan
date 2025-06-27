package entities;

public abstract class Entity {
    protected String name;
    protected int hp;       // PV courants
    protected int maxHp;    // PV maximum
    protected int attack;
    protected int defense;

    
    public Entity(String name, int hp, int attack, int defense) {
        this.name    = name;
        this.hp      = hp;
        this.maxHp   = hp;
        this.attack  = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public abstract int getDefense();

    public boolean isAlive() {
        return hp > 0;
    }

    /* Inflige des dégâts et ne descend pas sous zéro */
    public void receiveDamage(int amount) {
        int effectiveDamage = Math.max(0, amount - defense);
        hp -= effectiveDamage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    /* Soigne sans dépasser maxHp */
    public void receiveHealing(int amount) {
        this.hp = Math.min(this.hp + amount, this.maxHp);
    }
}
