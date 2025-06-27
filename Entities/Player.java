package entities;
import items.Equipment;
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
        maxHp += 10;          // Augmente le maximum de PV
        hp += 10;             // Donne aussi des PV courants
        if (hp > maxHp) {     // Sans dépasser le maximum
            hp = maxHp;
        }
        attack += 2;          // Bonus d'attaque
        defense += 2;         // Bonus de défense
        DisplayUtils.display(
            name + " monte de niveau ! Niveau: " + level +
            " (PV: " + hp + "/" + maxHp +
            ", Attaque: " + attack +
            ", Défense: " + defense + ")"
        );
    }

    public void addItem(Item item) {
        inventory.add(item);
        DisplayUtils.display(name + " récupère: " + item.getName());
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void useItem(int index) {
    if (index < 0 || index >= inventory.size()) return;
    Item item = inventory.get(index);
    if (!(item instanceof Potion)) {
        DisplayUtils.display("Cet objet ne peut pas être utilisé.");
        return;
    }

    // On a une potion
    Potion p = (Potion) item;
    if (getHp() == getMaxHp()) {
        DisplayUtils.display("Vos PV sont déjà au maximum, vous ne pouvez pas en boire.");
        return;
    }

    // Calcul de la quantité réellement soignée (pour l'affichage)
    int possibleHeal = getMaxHp() - getHp();
    int healAmount   = Math.min(p.getHealAmount(), possibleHeal);

    // Soigne et retire la potion de l'inventaire
    receiveHealing(p.getHealAmount());
    inventory.remove(index);

    DisplayUtils.display(
        "Vous buvez " + p.getName() +
        " et récupérez " + healAmount + " PV."
    );
}

     public void equipItem(int index) {
        if (index < 0 || index >= inventory.size()) return;
        Item item = inventory.get(index);
        if (item instanceof Equipment) {
            Equipment eq = (Equipment) item;
            if (eq instanceof Weapon) {
                equipWeapon((Weapon) eq);
            } else if (eq instanceof Armor) {
                equipArmor((Armor) eq);
            }
        } else {
            DisplayUtils.display("Cet objet ne peut pas être équipé.");
        }
    }

    public void equipWeapon(Weapon weapon) {
        if (equippedWeapon != null) {
            equippedWeapon.setEquipped(false);
        }
        this.equippedWeapon = weapon;
        weapon.setEquipped(true);
        DisplayUtils.display(name + " équipe l'arme : " + weapon.getName());
        displayStats();
    }

    public void equipArmor(Armor armor) {
        if (equippedArmor != null) {
            equippedArmor.setEquipped(false);
        }
        this.equippedArmor = armor;
        armor.setEquipped(true);
        DisplayUtils.display(name + " équipe l'armure : " + armor.getName());
        displayStats();
    }

    /** Affiche les PV, l'attaque et la défense actuels */
    private void displayStats() {
        DisplayUtils.display(
            "Statistiques → PV: " + getHp() + "/" + getMaxHp() +
            ", Attaque: " + getAttack() +
            ", Défense: " + getDefense()
        );
    }

    @Override
    public int getAttack() {
        int bonus = (equippedWeapon != null) ? equippedWeapon.getAttackBonus() : 0;
        return attack + bonus;
    }

    @Override
    public int getDefense() {
        int bonus = (equippedArmor != null) ? equippedArmor.getDefenseBonus() : 0;
        return defense + bonus;
    }
}