package utils;

import java.util.Scanner;
import java.util.List;
import entities.Player;
import items.Item;
import items.Potion;
import items.Weapon;
import items.Armor;

public class InventoryUtils {
    /* Affiche et gère l’inventaire du joueur */
    public static void openInventory(Player player, Scanner scanner) {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            DisplayUtils.display("Votre inventaire est vide.");
            return;
        }
        while (true) {
            DisplayUtils.display("=== Inventaire ===");
            for (int i = 0; i < inventory.size(); i++) {
                Item item = inventory.get(i);
                DisplayUtils.display("[" + i + "] " + item.getName());
            }
            DisplayUtils.display("Entrez le numéro pour utiliser/équiper, ou 'q' pour quitter.");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) break;
            try {
                int idx = Integer.parseInt(input);
                if (idx < 0 || idx >= inventory.size()) {
                    DisplayUtils.display("Choix invalide.");
                    continue;
                }
                Item item = inventory.get(idx);
                if (item instanceof Potion) {
                    player.useItem(idx);
                } else if (item instanceof Weapon || item instanceof Armor) {
                    player.equipItem(idx);
                } else {
                    DisplayUtils.display("Vous ne pouvez rien faire avec cet objet.");
                }
            } catch (NumberFormatException e) {
                DisplayUtils.display("Entrée invalide.");
            }
        }
    }
}
