package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventoryInit {
    public static List<ArtefaktModel> artefaktModels = new ArrayList<>();

    public static void loadArtefaktModels() {
        System.out.println("loadArtefaktModels() called from:");
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(ste);
        }

        artefaktModels.clear(); // Dodaj tę linię

        for (String ID : getArtefaktyInventory.yamlData.getConfig().getConfigurationSection("artefakty").getKeys(false)) {
            System.out.println("Aktualne ID: " + ID);
            System.out.println("Odczytano klucz: " + ID);

            // Dodaj te linie, aby wydrukować ścieżkę i nazwę pliku
            File configFile = getArtefaktyInventory.yamlData.getConfigFile();
            System.out.println("Ścieżka do pliku: " + configFile.getAbsolutePath());
            System.out.println("Nazwa pliku: " + configFile.getName());

            ArtefaktModel artefaktModel = new ArtefaktModel();

            String materialName = getArtefaktyInventory.yamlData.getConfig().getString("artefakty." + ID + ".ItemStack");
            String displayName = getArtefaktyInventory.yamlData.getConfig().getString("artefakty." + ID + ".name"); // Dodane wczytywanie nazwy

            // Dodane logowanie klucza "lore"
            System.out.println("Klucz lore: " + "artefakty." + ID + ".Lore");

            List<String> lore = getArtefaktyInventory.yamlData.getConfig().getStringList("artefakty." + ID + ".Lore"); // Dodane wczytywanie lore

            // Dodane logowanie wartości "lore"
            System.out.println("Wartość lore: " + lore);

            ItemStack itemStack;
            if (materialName != null) {
                itemStack = new ItemStack(Material.valueOf(materialName));
                artefaktModel.setItemStack(itemStack);
            } else {
                System.out.println("Błąd: Brak nazwy materiału dla artefaktu " + ID);
                continue;
            }

            int slotInEq = getArtefaktyInventory.yamlData.getConfig().getInt("artefakty." + ID + ".SlotInEq");
            int maxInEq = getArtefaktyInventory.yamlData.getConfig().getInt("artefakty." + ID + ".MaxInEq");
            artefaktModel.setSlotInEq(slotInEq);
            artefaktModel.setMaxInEq(maxInEq);

            if (displayName != null) {
                artefaktModel.setName(displayName); // Ustawienie nazwy modelu artefaktu
            } else {
                System.out.println("Błąd: Brak nazwy wyświetlanej dla artefaktu " + ID);
            }

            ItemMeta meta = itemStack.getItemMeta(); // Pobierz ItemMeta z ItemStack
            if (displayName != null) {
                meta.setDisplayName(displayName); // Ustaw nazwę wyświetlaną
            }
            if (lore != null && !lore.isEmpty()) {
                meta.setLore(lore); // Ustaw lore
            }
            itemStack.setItemMeta(meta); // Zastosuj zmiany do ItemStack

            artefaktModels.add(artefaktModel);

            // Dodaj logi
            System.out.println("Loaded artefakt: " + ID);
            System.out.println("ItemStack: " + itemStack);
            System.out.println("SlotInEq: " + slotInEq);
            System.out.println("MaxInEq: " + maxInEq);
            System.out.println("Name: " + displayName); // Dodane logowanie nazwy
            System.out.println("Lore: " + lore); // Dodane logowanie lore
        }
    }

}
