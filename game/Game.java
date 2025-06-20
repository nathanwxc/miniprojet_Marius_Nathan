package game;

import java.util.Scanner;
import entities.Player;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dungeon dungeon = MapGenerator.generateDungeon(4, 4);
        
        System.out.println("Bienvenue dans le donjon !");
        // Création du héros avec des stats de base
        Player player = new Player("Héros", 100, 10, 5);
        int playerX = 0, playerY = 0;
        
        while (player.isAlive()) {
            Room currentRoom = dungeon.getRoom(playerX, playerY);
            System.out.println("\nVous êtes dans la salle (" + playerX + ", " + playerY + ").");
            System.out.println("Portes disponibles :");
            if (currentRoom.hasDoorNorth()) System.out.println(" - NORD");
            if (currentRoom.hasDoorEast())  System.out.println(" - EST");
            if (currentRoom.hasDoorSouth()) System.out.println(" - SUD");
            if (currentRoom.hasDoorWest())  System.out.println(" - OUEST");

            // On effectue l'action de la salle
            currentRoom.enter(player);
            if (currentRoom.getContent() == Room.RoomContent.EXIT) {
                System.out.println("Félicitations, vous avez quitté le donjon !");
                break;
            }
            
            System.out.println("Dans quelle direction souhaitez-vous aller ? (N, E, S, W)");
            String direction = scanner.nextLine().trim().toUpperCase();
            int nextX = playerX;
            int nextY = playerY;
            boolean canMove = false;
            switch(direction) {
                case "N":
                    if (currentRoom.hasDoorNorth()) { nextY--; canMove = true; }
                    break;
                case "E":
                    if (currentRoom.hasDoorEast()) { nextX++; canMove = true; }
                    break;
                case "S":
                    if (currentRoom.hasDoorSouth()) { nextY++; canMove = true; }
                    break;
                case "W":
                    if (currentRoom.hasDoorWest()) { nextX--; canMove = true; }
                    break;
                default:
                    System.out.println("Direction non valide.");
            }
            if (!canMove) {
                System.out.println("Vous ne pouvez pas aller dans cette direction.");
            } else if (dungeon.getRoom(nextX, nextY) == null) {
                System.out.println("Il n'y a pas de salle dans cette direction.");
            } else {
                playerX = nextX;
                playerY = nextY;
            }
        }
        
        if (!player.isAlive()) {
            System.out.println("Vous êtes mort. Le donjon vous a eu...");
        }
        scanner.close();
    }
}