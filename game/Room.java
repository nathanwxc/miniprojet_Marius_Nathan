package game;

import entities.Monster;
import entities.Player;
import items.Armor;
import items.Item;
import items.Potion;
import items.Weapon;
import java.util.Scanner;
import utils.DisplayUtils;
import utils.RandomUtils;

public class Room {
    public enum RoomContent {
        EMPTY, MONSTER, LOOT, EXIT
    }

    private int x, y;
    private boolean doorNorth, doorSouth, doorEast, doorWest;
    private RoomContent content;
    private Monster monster;
    private Item lootItem;              

    public Room(int x, int y) {
        this.x = x; this.y = y;
        this.doorNorth = this.doorSouth = this.doorEast = this.doorWest = false;
        generateContents();
    }


    public RoomContent getContent() { return content; }

    public void generateContents() {
        int roll = RandomUtils.nextInt(100);
        if (roll < 10) {
            content = RoomContent.EMPTY;
        }
        else if (roll < 60) {
            content = RoomContent.MONSTER;
            int monsterRoll = RandomUtils.nextInt(100);
            if (monsterRoll < 50) {
                monster = new Monster("Gobelin", 20, 10, 2);
            } else {
                int hp      = 50 + RandomUtils.nextInt(51);
                int atk     = 10 + RandomUtils.nextInt(11);
                int def     = 5  + RandomUtils.nextInt(6);
                monster = new Monster("RandomBoss", hp, atk, def);
            }
        }
        else if (roll < 99) {
            content = RoomContent.LOOT;
            lootItem = generateLootItem();   // on prépare le loot
        }
        else {
            content = RoomContent.EXIT;
        }
    }

    /** Génère un objet aléatoire (potion, arme ou armure) */
    private Item generateLootItem() {
        int r = RandomUtils.nextInt(100);
        if (r < 50) {
            return new Potion("Potion de soin", 20);
        } 
        else if (r < 65) {
            int bonus = 5 + RandomUtils.nextInt(6);       // 5–10 ATK
            return new Weapon("Épée courte", bonus);
        } 
        else if (r < 80) {
            int bonus = 3 + RandomUtils.nextInt(4);       // 3–6 DEF
            return new Armor("Armure légère", bonus);
        }
        else if (r < 90) {
            int bonus = 10 + RandomUtils.nextInt(6);       // 10–15 ATK
            return new Weapon("Épée de la Morkitu", bonus);
        } 
        else {
            int bonus = 6 + RandomUtils.nextInt(4);       // 6–9 DEF
            return new Armor("Armure de la Morkitu", bonus);
        }
    }

     public void enter(Player player, Scanner scanner) {
        DisplayUtils.display("Vous entrez dans la salle (" + x + ", " + y + ").");
        switch (content) {
            case MONSTER:
                if (monster != null) {
                    DisplayUtils.display("Un " + monster.getName() + " apparaît !");
                    combat(player, monster, scanner);
                    if (!monster.isAlive()) {
                        DisplayUtils.display(monster.getName() + " est vaincu !");

                        // → XP proportionnel à la force du monstre
                        int xpGain = monster.getAttack() + monster.getDefense()+50;
                        player.gainXP(xpGain);

                        Item drop = generateLootItem();
                        DisplayUtils.display("Le monstre laisse tomber : " + drop.getName());
                        player.addItem(drop);
                        content = RoomContent.EMPTY;
                    }

                }
                break;

            case LOOT:
                DisplayUtils.display("Vous trouvez : " + lootItem.getName());
                player.addItem(lootItem);
                content = RoomContent.EMPTY;
                break;

            case EXIT:
                DisplayUtils.display("Vous avez trouvé la sortie du donjon !");
                break;

            case EMPTY:
            default:
                DisplayUtils.display("La salle est vide.");
        }
    }

    private void combat(Player player, Monster monster, Scanner scanner) {
    DisplayUtils.display("--- Combat commencé ---");
    while (player.isAlive() && monster.isAlive()) {
        // Proposition de fuite
        DisplayUtils.display("Appuyez sur 'F' pour fuir ou Entrée pour attaquer.");
        String action = scanner.nextLine();
        if (action.equalsIgnoreCase("F")) {
            int baseChance = 80;                                     // 80% de base
            int diff      = monster.getAttack() - player.getAttack(); // différence d'attaque
            int fleeChance = baseChance - diff * 3;                   // -3% par point de diff

            // Clamp entre 5% et 95%
            if (fleeChance < 5)   fleeChance = 5;
            if (fleeChance > 95)  fleeChance = 95;
            int roll = RandomUtils.nextInt(100) + 1;
            if (roll <= fleeChance) {
                DisplayUtils.display("Vous fuyez avec succès !");
                return;
            } else {
                DisplayUtils.display("Échec de la fuite !");
                DisplayUtils.display(monster.getName() + " vous frappe pendant votre fuite !");
                player.receiveDamage(monster.getAttack());
                DisplayUtils.display(player.getName() + " : " + player.getHp() + "/" + player.getMaxHp() + " PV restants");
                if (!player.isAlive()) {
                    DisplayUtils.display("Vous n'avez pas survécu en fuyant...");
                    return;
                }
                continue;
            }
        }
        // Tour d'attaque du joueur
        DisplayUtils.display(player.getName() + " attaque " + monster.getName());
        monster.receiveDamage(player.getAttack());
        DisplayUtils.display(monster.getName() + " : " + monster.getHp() + " PV restants");
        if (!monster.isAlive()) break;

        // Riposte du monstre
        DisplayUtils.display(monster.getName() + " attaque " + player.getName());
        player.receiveDamage(monster.getAttack());
        DisplayUtils.display(player.getName() + " : " + player.getHp() + "/" + player.getMaxHp() + " PV restants");

        
    }
}

    // getters de position
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean hasDoorNorth() { return doorNorth; }
    public boolean hasDoorSouth() { return doorSouth; }
    public boolean hasDoorEast()  { return doorEast; }
    public boolean hasDoorWest()  { return doorWest; }

    // setters de portes
    public void setDoorNorth(boolean doorNorth) { this.doorNorth = doorNorth; }
    public void setDoorSouth(boolean doorSouth) { this.doorSouth = doorSouth; }
    public void setDoorEast(boolean doorEast)   { this.doorEast  = doorEast; }
    public void setDoorWest(boolean doorWest)   { this.doorWest  = doorWest; }
}
