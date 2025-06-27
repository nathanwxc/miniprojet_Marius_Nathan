package items;

public class Armor extends Equipment {
    private int defenseBonus;

    public Armor(String name, int defenseBonus) {
        super(name);
        this.defenseBonus = defenseBonus;
    }

    @Override
    public int getAttackBonus() {
        return 0;
    }

    @Override
    public int getDefenseBonus() {
        return defenseBonus;
    }
}