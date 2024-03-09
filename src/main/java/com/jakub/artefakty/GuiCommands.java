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

public class GuiCommands implements CommandExecutor, Listener {

    private createCustomItem customItemCreator;
    private getArtefaktyInventory artefaktyInventory;

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

            // Dodaj to sprawdzenie
            if (event.getView().getTitle().equals("§6Twoje Trofea")) {
                // Sprawdź, czy kliknięty slot to jeden z wymienionych
                if (clickedSlot == 10 || clickedSlot == 11 || clickedSlot == 12 || clickedSlot == 14 || clickedSlot == 15 || clickedSlot == 16) {
                    ItemStack clickedItem = event.getCurrentItem(); // Pobierz kliknięty przedmiot

                    // Sprawdź, czy gracz ma taki przedmiot
                    if (removeItem(player, clickedItem)) {
                        player.sendMessage("Dodano " + clickedItem.getItemMeta().getDisplayName()); // Wyślij wiadomość o dodaniu
                    } else {
                        player.sendMessage("§eBłąd, nie posiadasz przedmiotu " + clickedItem.getItemMeta().getDisplayName() + " w swoim ekwipunku"); // Wyślij wiadomość o błędzie
                    }
                }
            }
        }
    }


}

