package entities;

import items.Item;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private int xp;
    private int level;
    private List<Item> inventory;
  

    public Player(String name, int hp, int attack, int defense) {
        super(name, hp, attack, defense);
        this.xp = 0;
        this.level = 1;
        inventory = new ArrayList<>();
    }

    public void gainXP(int amount) {
        xp += amount;
        System.out.println(name + " gagne " + amount + " XP. Total: " + xp);
        if (xp >= level * 100) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        hp += 10;
        attack += 2;
        defense += 2;
        System.out.println(name + " monte de niveau ! Niveau: " + level + " (HP: " + hp 
                           + ", Attaque: " + attack + ", Défense: " + defense + ")");
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println(name + " récupère: " + item.getName());
    }

    public List<Item> getInventory() {
        return inventory;
    }
}