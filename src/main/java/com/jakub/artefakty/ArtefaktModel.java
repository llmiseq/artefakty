package com.jakub.artefakty;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ArtefaktModel {

    private MyPlugin myPluginInstance;
    private ItemStack itemStack;
    private int slotInEq;
    private int maxInEq;
    private List<String> bonuses;

    public ArtefaktModel() {
        this.myPluginInstance = MyPlugin.getInstance(); // Pobierz instancję twojego pluginu
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public int getSlotInEq() {
        return slotInEq;
    }

    public void setSlotInEq(int slotInEq) {
        this.slotInEq = slotInEq;
    }

    public int getMaxInEq() {
        return maxInEq;
    }

    public void setMaxInEq(int maxInEq) {
        this.maxInEq = maxInEq;
    }

    public List<String> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<String> bonuses) {
        this.bonuses = bonuses;
    }

    public ItemStack getPlayerItem(Player player){
        ItemStack itemStack1 = this.itemStack.clone();
        ItemMeta im = itemStack1.getItemMeta();
        im.setLore(List.of(player.getLevel()+""));
        itemStack1.setItemMeta(im);
        return itemStack1;
    }
}
