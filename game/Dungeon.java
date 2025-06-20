package game;

import utils.RandomUtils;

public class Dungeon {
    private Room[][] rooms;
    private int width, height;
    
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        rooms = new Room[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                rooms[y][x] = new Room(x, y);
            }
        }
        generateMaze();
    }
    
    private void generateMaze() {
        // Génération simplifiée des connexions entre salles.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Room room = rooms[y][x];
                if (y > 0 && RandomUtils.coinFlip()) {
                    room.setDoorNorth(true);
                    rooms[y - 1][x].setDoorSouth(true);
                }
                if (x < width - 1 && RandomUtils.coinFlip()) {
                    room.setDoorEast(true);
                    rooms[y][x + 1].setDoorWest(true);
                }
                if (y < height - 1 && RandomUtils.coinFlip()) {
                    room.setDoorSouth(true);
                    rooms[y + 1][x].setDoorNorth(true);
                }
                if (x > 0 && RandomUtils.coinFlip()) {
                    room.setDoorWest(true);
                    rooms[y][x - 1].setDoorEast(true);
                }
            }
        }
    }
    
    public Room getRoom(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            return rooms[y][x];
        return null;
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
