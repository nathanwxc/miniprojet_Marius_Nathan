package items;

public class Potion extends Item {
    private int healAmount;

    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    public int getHealAmount() {
        return healAmount;
    }

    @Override
    public String toString() {
        return name + " (Heal " + healAmount + ")";
    }
}
