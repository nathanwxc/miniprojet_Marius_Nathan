package items;

public abstract class Equipment extends Item {
    private boolean equipped = false;

    public Equipment(String name) {
        super(name);
    }

    /* Bonus d’attaque conféré par cet équipement */
    public abstract int getAttackBonus();

    /* Bonus de défense conféré par cet équipement */
    public abstract int getDefenseBonus();

    /* État équipé / non équipé */
    public boolean isEquipped() {
        return equipped;
    }

    /* Définit l’état équipé */
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}