package entities;

import utils.DisplayUtils;
import items.Item;
import items.Potion;
import items.Weapon;
import items.Armor;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private int xp;
    private int level;
    private List<Item> inventory;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    public Player(String name, int hp, int attack, int defense) {
        super(name, hp, attack, defense);
        this.xp = 0;
        this.level = 1;
        this.inventory = new ArrayList<>();
    }

    public void gainXP(int amount) {
        xp += amount;
        DisplayUtils.display(name + " gagne " + amount + " XP. Total: " + xp);
        if (xp >= level * 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        hp += 10;
        attack += 2;
        defense += 2;
        DisplayUtils.display(name + " monte de niveau ! Niveau: " + level +
                             " (HP: " + hp + ", Attaque: " + attack +
                             ", Défense: " + defense + ")");
    }

    public void addItem(Item item) {
        inventory.add(item);
        DisplayUtils.display(name + " récupère: " + item.getName());
    }

    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
        DisplayUtils.display(name + " équipe: " + weapon.getName());
    }

    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
        DisplayUtils.display(name + " équipe: " + armor.getName());
    }

    @Override
    public int getAttack() {
        int bonus = (equippedWeapon != null) ? equippedWeapon.getAttackBonus() : 0;
        return attack + bonus;
    }

    public int getDefense() {
        int bonus = (equippedArmor != null) ? equippedArmor.getDefenseBonus() : 0;
        return defense + bonus;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    /** Utilise un objet (uniquement les potions) */
    public void useItem(int index) {
        if (index < 0 || index >= inventory.size()) return;
        Item item = inventory.get(index);
        if (item instanceof Potion) {
            Potion p = (Potion) item;
            this.receiveHealing(p.getHealAmount());
            inventory.remove(index);
            DisplayUtils.display("Vous buvez " + p.getName() +
                                 " et récupérez " + p.getHealAmount() + " PV.");
        } else {
            DisplayUtils.display("Cet objet ne peut pas être utilisé.");
        }
    }

    /** Équipe une arme ou une armure depuis l’inventaire */
    public void equipItem(int index) {
        if (index < 0 || index >= inventory.size()) return;
        Item item = inventory.get(index);

        if (item instanceof Weapon) {
            equipWeapon((Weapon) item);
        } else if (item instanceof Armor) {
            equipArmor((Armor) item);
        } else {
            DisplayUtils.display("Cet objet ne peut pas être équipé.");
        }
    }
}
