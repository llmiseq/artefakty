package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.*;

import static javax.swing.text.html.parser.DTDConstants.ID;

public class InventoryInit {
    public static List<ArtefaktModel> artefaktModels = new ArrayList<>();

    public static List<ArtefaktModel> artefaktModelList = new ArrayList<>();


    public static void loadArtefaktModels() {
        // Dodaj logi
        System.out.println("loadArtefaktModels() called from:");
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(ste);
        }

        artefaktModels.clear(); // Dodaj tę linię
        artefaktModelList.clear(); // Dodane czyszczenie listy

        for (String ID : getArtefaktyInventory.yamlData.getConfig().getConfigurationSection("artefakty").getKeys(false)) {
            System.out.println("Aktualne ID: " + ID);
            System.out.println("Odczytano klucz: " + ID);

            // Dodaj te linie, aby wydrukować ścieżkę i nazwę pliku
            File configFile = getArtefaktyInventory.yamlData.getConfigFile();
            System.out.println("Ścieżka do pliku: " + configFile.getAbsolutePath());
            System.out.println("Nazwa pliku: " + configFile.getName());

            ArtefaktModel artefaktModel = new ArtefaktModel();

            String materialName = getArtefaktyInventory.yamlData.getConfig().getString("artefakty." + ID + ".ItemStack");

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

            artefaktModels.add(artefaktModel);

            // Dodaj logi
            System.out.println("Loaded artefakt: " + ID);
            System.out.println("ItemStack: " + itemStack);
            System.out.println("SlotInEq: " + slotInEq);
            System.out.println("MaxInEq: " + maxInEq);
        }

        // Dodaj logi dla setlisty artefaktModels
        for (ArtefaktModel model : artefaktModels) {
            System.out.println("ArtefaktModel: " + model);
            System.out.println("ItemStack: " + model.getItemStack());
            System.out.println("SlotInEq: " + model.getSlotInEq());
            System.out.println("MaxInEq: " + model.getMaxInEq());
        }
    }
}
