package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InventoryInit {


    /*
        Ta klasa jest odpowiedzialna za ładowanie GUI gracza na bazie configu Artefakty.java
        <--UWAGA!--> na w linijkach 93-98 zawiera ona dodatkowe logi odnośnie do poprawnego załadowania
        GUI
    */
    public static List<ArtefaktModel> artefaktModels = new ArrayList<>();

    public static void loadArtefaktModels() {
        System.out.println("loadArtefaktModels() called from:");
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(ste);
        }

        artefaktModels.clear();

        for (String ID : getArtefaktyInventory.yamlData.getConfig().getConfigurationSection("artefakty").getKeys(false)) {
            System.out.println("Aktualne ID: " + ID);
            System.out.println("Odczytano klucz: " + ID);

            File configFile = getArtefaktyInventory.yamlData.getConfigFile();

            ArtefaktModel artefaktModel = new ArtefaktModel();

            String materialName = getArtefaktyInventory.yamlData.getConfig().getString("artefakty." + ID + ".ItemStack");
            //System.out.println("Nazwa materiału: " + materialName);

            String displayName = getArtefaktyInventory.yamlData.getConfig().getString("artefakty." + ID + ".name");
            //System.out.println("Nazwa wyświetlana: " + displayName);

            //System.out.println("Klucz lore: " + "artefakty." + ID + ".Lore");

            List<String> lore = getArtefaktyInventory.yamlData.getConfig().getStringList("artefakty." + ID + ".Lore");
            //System.out.println("Wartość lore: " + lore);

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
            System.out.println("SlotInEq: " + slotInEq);
            System.out.println("MaxInEq: " + maxInEq);

            artefaktModel.setSlotInEq(slotInEq);
            artefaktModel.setMaxInEq(maxInEq);

            //System.out.println("Załadowano MaxInEq dla " + ID + ": " + maxInEq);

            if (displayName != null) {
                artefaktModel.setName(displayName);
            } else {
                System.out.println("Błąd: Brak nazwy wyświetlanej dla artefaktu " + ID);
            }

            ItemMeta meta = itemStack.getItemMeta();
            if (displayName != null) {
                meta.setDisplayName(displayName);
            }
            if (lore != null && !lore.isEmpty()) {
                meta.setLore(lore);
            }

            // Dodaj klucz do PersistentDataContainer
            NamespacedKey key = new NamespacedKey(Artefakty.getInstance(), "artefactKey");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, displayName); // Zmieniono ID na displayName

            itemStack.setItemMeta(meta);

            artefaktModel.setItemStack(itemStack);
            artefaktModels.add(artefaktModel);

            System.out.println("Załadowano model artefaktu: " + artefaktModel.getName());
            System.out.println("Loaded artefakt: " + ID);
            System.out.println("ItemStack: " + itemStack);
            System.out.println("Name: " + displayName);
            System.out.println("Lore: " + lore);
        }
    }

}
