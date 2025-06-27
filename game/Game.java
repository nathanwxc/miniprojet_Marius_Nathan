package game;

import java.util.Scanner;
import entities.Player;
import utils.DisplayUtils;
import utils.InventoryUtils;



public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dungeon dungeon = MapGenerator.generateDungeon(4, 4);
        
        DisplayUtils.display("Bienvenue dans le donjon !");
        // Création du héros avec des stats de base
        Player player = new Player("Héros", 100, 10, 5);
        int playerX = 0, playerY = 0;
        
        while (player.isAlive()) {
            Room currentRoom = dungeon.getRoom(playerX, playerY);
            
            // On effectue l'action de la salle
            currentRoom.enter(player);
            if (currentRoom.getContent() == Room.RoomContent.EXIT) {
                DisplayUtils.display("Félicitations, vous avez quitté le donjon !");
                break;
            }
            // Gestion de l’inventaire
            DisplayUtils.display("Appuyez sur 'I' pour ouvrir l'inventaire, ou Entrée pour continuer.");
            String invInput = scanner.nextLine();
            if (invInput.equalsIgnoreCase("I")) {
                InventoryUtils.openInventory(player, scanner);
            }
            DisplayUtils.display("\nVous êtes dans la salle (" + playerX + ", " + playerY + ").");
            DisplayUtils.display("Portes disponibles :");
            if (currentRoom.hasDoorNorth()) DisplayUtils.display(" - NORD");
            if (currentRoom.hasDoorEast())  DisplayUtils.display(" - EST");
            if (currentRoom.hasDoorSouth()) DisplayUtils.display(" - SUD");
            if (currentRoom.hasDoorWest())  DisplayUtils.display(" - OUEST");


            DisplayUtils.display("Dans quelle direction souhaitez-vous aller ? (N, E, S, W)");
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
                    DisplayUtils.display("Direction non valide.");
            }
            if (!canMove) {
                DisplayUtils.display("Vous ne pouvez pas aller dans cette direction.");
            } else if (dungeon.getRoom(nextX, nextY) == null) {
                DisplayUtils.display("Il n'y a pas de salle dans cette direction.");
            } else {
                playerX = nextX;
                playerY = nextY;
            }
        }
        
        if (!player.isAlive()) {
            DisplayUtils.display("Vous êtes mort. Le donjon vous a eu...");
        }
        scanner.close();
    }
}