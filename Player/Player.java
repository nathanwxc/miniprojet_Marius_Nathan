package miniprojet_Marius_Nathan.java
public class Player {
    private final String name;
    private int hp = 100;

    public Player(String name) {
        this.name = name;
    }

    public void greet() {
        System.out.println("Bonjour, je suis " + name + " !");
    }

    public void takeDamage(int amount) {
        hp -= amount;
        System.out.println(name + " a maintenant " + hp + " HP");
    }

    public boolean isAlive() {
        return hp > 0;
    }
    public int getHp() {
        return hp;
    }

    public static void main(String[] args) {
        Player p = new Player("bob(pas le bricoleur du coup)");
        p.greet();
        p.takeDamage(15);
        Weapon sabrelazer = new Weapon(10, 20);
        int damage = sabrelazer.rollDamage();
        System.out.println("L'arme inflige " + damage + " dégâts !");
        p.takeDamage(damage);
    }
}