package entities;




public class Player extends Entity {
    private int xp;
    private int level;
  

    public Player(String name, int hp, int attack, int defense) {
        super(name, hp, attack, defense);
        this.xp = 0;
        this.level = 1;
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

    
}