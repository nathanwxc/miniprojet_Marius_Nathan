package miniprojet_Marius_Nathan.Weapons;
import java.util.Random;

public class Weapons {
    private final int minDamage;
    private final int maxDamage;
    private final Random rand = new Random();

    public Weapons(int minDamage, int maxDamage) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public int rollDamage() {
        return rand.nextInt(maxDamage - minDamage + 1) + minDamage;
    }
}
