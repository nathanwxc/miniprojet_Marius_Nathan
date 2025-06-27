package game;
import entities.Monster;
import entities.Player;
import items.Potion;
import utils.DisplayUtils;
import utils.RandomUtils;

public class Room {
    public enum RoomContent {
        EMPTY, MONSTER, LOOT, EXIT
    }

    private int x, y;
    private boolean doorNorth;
    private boolean doorSouth;
    private boolean doorEast;
    private boolean doorWest;
    private RoomContent content;
    
    // Éventuellement, conserver une référence vers l'entité présente
    private Monster monster;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
        // Les portes seront définies par le générateur de carte.
        doorNorth = false;
        doorSouth = false;
        doorEast = false;
        doorWest = false;
        generateContents();
    }

    public void setDoorNorth(boolean doorNorth) { this.doorNorth = doorNorth; }
    public void setDoorSouth(boolean doorSouth) { this.doorSouth = doorSouth; }
    public void setDoorEast(boolean doorEast) { this.doorEast = doorEast; }
    public void setDoorWest(boolean doorWest) { this.doorWest = doorWest; }

    public boolean hasDoorNorth() { return doorNorth; }
    public boolean hasDoorSouth() { return doorSouth; }
    public boolean hasDoorEast()  { return doorEast; }
    public boolean hasDoorWest()  { return doorWest; }
    
    public RoomContent getContent() { return content; }
    
    public void generateContents() {
    int roll = RandomUtils.nextInt(100);
    if (roll < 10) {
        content = RoomContent.EMPTY;
    } else if (roll < 60) {
        content = RoomContent.MONSTER;
        int monsterRoll = RandomUtils.nextInt(100);
        if (monsterRoll > 50) {
            monster = new Monster("Gobelin", 20, 7, 2);
        } else if(monsterRoll < 10) {
            // Génération dynamique des stats du RandomBoss
            int bossHp      = 50 + RandomUtils.nextInt(51);  // 50 à 100 PV
            int bossAttack  = 10 + RandomUtils.nextInt(11);  // 10 à 20 d’attaque
            int bossDefense = 5  + RandomUtils.nextInt(6);   // 5 à 10 de défense
            monster = new Monster("RandomBoss", bossHp, bossAttack, bossDefense);
        }
        else if(50 <monsterRoll && monsterRoll< 10){
             monster = new Monster("Ravageur", 50, 10, 4);
        }
    } else if (roll < 75) {
        content = RoomContent.LOOT;
        // loot potion…
    } else if (roll < 99) {
        content = RoomContent.LOOT;
    } else {
        content = RoomContent.EXIT;
    }
}

    public void enter(Player player) {
        DisplayUtils.display("Vous entrez dans la salle (" + x + ", " + y + ").");
        switch (content) {
            case MONSTER:
                if (monster != null) {
                    DisplayUtils.display("Un " + monster.getName() + " apparaît !");
                    combat(player, monster);
                    if (!monster.isAlive()) {
                        DisplayUtils.display(monster.getName() + " est vaincu !");

                        // → XP proportionnel à la force du monstre
                        int xpGain = monster.getAttack() + monster.getDefense()+50;
                        player.gainXP(xpGain);
                        content = RoomContent.EMPTY;
                    }
                }
                break;
            case LOOT:
                DisplayUtils.display("Vous trouvez une potion de soin.");
                player.addItem(new Potion("Potion de soin", 20));
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

    private void combat(Player player, Monster monster) {
        DisplayUtils.display("--- Combat ---");
        while (player.isAlive() && monster.isAlive()) {
            DisplayUtils.display(player.getName() + " attaque " + monster.getName());
            monster.receiveDamage(player.getAttack());
            if (!monster.isAlive())
                break;
            DisplayUtils.display(monster.getName() + " attaque " + player.getName());
            player.receiveDamage(monster.getAttack());
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
}