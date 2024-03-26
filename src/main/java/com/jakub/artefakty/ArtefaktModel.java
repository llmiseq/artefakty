package com.jakub.artefakty;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ArtefaktModel {
    private Artefakty myPluginInstance;
    private ItemStack itemStack;
    private int slotInEq;
    private int maxInEq;
    private List<String> bonuses;
    private String name;

    public ArtefaktModel() {
        this.myPluginInstance = Artefakty.getInstance(); // Pobierz instancję twojego pluginu
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

    public ItemStack getPlayerItem(Player player) {
        System.out.println("Wywołano metodę getPlayerItem dla gracza: " + player.getName());
        ItemStack itemStack1 = this.itemStack.clone();
        return itemStack1;
    }

    public String getName() {
        return name; // Zwraca wartość pola name
    }

    public void setName(String name) {
        this.name = name; // Ustawia wartość pola name
    }
}
