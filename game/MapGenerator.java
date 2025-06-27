package game;

public class MapGenerator {
    public static Dungeon generateDungeon(int width, int height) {
        return new Dungeon(width, height);
    }
}