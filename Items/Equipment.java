package items;

public abstract class Equipment extends Item {
    public Equipment(String name) {
        super(name);
    }

    /** Bonus d’attaque conféré par cet équipement */
    public abstract int getAttackBonus();

    /** Bonus de défense conféré par cet équipement */
    public abstract int getDefenseBonus();
}