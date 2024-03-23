package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class InventoryInit {
    public static Set<ArtefaktModel> artefaktModels = new HashSet<>();

    public InventoryInit() {
        for (String ID : getArtefaktyInventory.yamlData.getConfig().getConfigurationSection("artefakty").getKeys(false)) {
            ArtefaktModel artefaktModel = new ArtefaktModel();

            ItemStack itemStack = new ItemStack(Material.valueOf(getArtefaktyInventory.yamlData.getConfig().getString("artefakty."+ID+".ItemStack")));
            artefaktModel.setItemStack(itemStack);

            int slotInEq = getArtefaktyInventory.yamlData.getConfig().getInt("artefakty."+ID+".SlotInEq");
            int maxInEq = getArtefaktyInventory.yamlData.getConfig().getInt("artefakty."+ID+".MaxInEq");
            artefaktModel.setSlotInEq(slotInEq);
            artefaktModel.setMaxInEq(maxInEq);

            artefaktModels.add(artefaktModel);
        }
    }
}
