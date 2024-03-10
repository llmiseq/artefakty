package com.jakub.artefakty;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GuiCommands implements CommandExecutor, Listener {

    private createCustomItem customItemCreator;
    private getArtefaktyInventory artefaktyInventory;
    private Map<UUID, Map<String, Integer>> playerItems = new HashMap<>(); // Nowa mapa


    public GuiCommands(getArtefaktyInventory artefaktyInventory) {
        this.artefaktyInventory = artefaktyInventory;
        this.customItemCreator = new createCustomItem();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            player.sendMessage("§aOtwieram trofea...");
            player.openInventory(artefaktyInventory.getArtefaktyInventory(1, 2, 3, 4, 5, 6));
        }
        return true;
    }
    private boolean removeItem(Player player, ItemStack clickedItem) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == clickedItem.getType() && item.getItemMeta().getDisplayName().equals(clickedItem.getItemMeta().getDisplayName())) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    player.getInventory().remove(item);
                }
                player.updateInventory(); // Aktualizuj ekwipunek gracza
                return true;
            }
        }
        return false;
    }


    // Dodaj tę metodę do klasy GuiCommands
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            int clickedSlot = event.getSlot();

            if (event.getView().getTitle().equals("§6Twoje Trofea")) {
                if (clickedSlot == 10 || clickedSlot == 11 || clickedSlot == 12 || clickedSlot == 14 || clickedSlot == 15 || clickedSlot == 16) {
                    ItemStack clickedItem = event.getCurrentItem();

                    // Sprawdź, czy gracz ma już maksymalną liczbę przedmiotów
                    int maxItemsPerPlayer = 1;
                    Map<String, Integer> playerInventory = playerItems.getOrDefault(player.getUniqueId(), new HashMap<>());
                    int currentItems = playerInventory.getOrDefault(clickedItem.getItemMeta().getDisplayName(), 0);
                    if (currentItems >= maxItemsPerPlayer) {
                        player.sendMessage("§cNie możesz dodać więcej takich przedmiotów!");
                        return;
                    }

                    if (removeItem(player, clickedItem)) {
                        player.sendMessage("Dodano " + clickedItem.getItemMeta().getDisplayName());
                        playerInventory.put(clickedItem.getItemMeta().getDisplayName(), currentItems + 1); // Aktualizuj mapę
                        playerItems.put(player.getUniqueId(), playerInventory);
                    } else {
                        player.sendMessage("§eBłąd, nie posiadasz przedmiotu " + clickedItem.getItemMeta().getDisplayName() + " w swoim ekwipunku");
                    }
                }
            }
        }
    }

}

