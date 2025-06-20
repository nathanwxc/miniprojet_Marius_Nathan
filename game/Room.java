package game;

import entities.Monster;
import entities.Player;
import utils.RandomUtils;

public class Room {
    public enum RoomContent {
        EMPTY, MONSTER, EXIT
    }

    private int x, y;
    private boolean doorNorth;
    private boolean doorSouth;
    private boolean doorEast;
    private boolean doorWest;
    private RoomContent content;
    private Monster monster;

    public Room(int x, int y) {
        this.x = x;
        this.y = y;
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
        if (roll < 40) {
            content = RoomContent.EMPTY;
        } else if (roll < 60) {
            content = RoomContent.MONSTER;
            monster = new Monster("Gobelin", 20, 10, 2);
        } else {
            content = RoomContent.EXIT;
        }
    }

    public void enter(Player player) {
        System.out.println("Vous entrez dans la salle (" + x + ", " + y + ").");
        switch (content) {
            case MONSTER:
                if (monster != null) {
                    System.out.println("Un " + monster.getName() + " apparaît !");
                    combat(player, monster);
                    if (!monster.isAlive()) {
                        System.out.println(monster.getName() + " est vaincu !");
                        player.gainXP(50);
                        content = RoomContent.EMPTY;
                    }
                }
                break;
            case EXIT:
                System.out.println("Vous avez trouvé la sortie du donjon !");
                break;
            case EMPTY:
            default:
                System.out.println("La salle est vide.");
        }
    }

    private void combat(Player player, Monster monster) {
        System.out.println("--- Combat ---");
        while (player.isAlive() && monster.isAlive()) {
            System.out.println(player.getName() + " attaque " + monster.getName());
            monster.receiveDamage(player.getAttack());
            if (!monster.isAlive())
                break;
            System.out.println(monster.getName() + " attaque " + player.getName());
            player.receiveDamage(monster.getAttack());
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
}