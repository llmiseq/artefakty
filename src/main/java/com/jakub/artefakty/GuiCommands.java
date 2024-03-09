package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class GuiCommands implements CommandExecutor, Listener {

    private createCustomItem customItemCreator;
    private getArtefaktyInventory artefaktyInventory;

    // Dodaj sześć zmiennych boolean
    private boolean czyPosiadaneRog = false;
    private boolean czyPosiadaneSlepie = false;
    private boolean czyPosiadaneBerlo = false;
    private boolean czyPosiadaneKlepsydra = false;
    private boolean czyPosiadaneKosa = false;
    private boolean czyPosiadaneFlogiston = false;

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

    // Dodaj tę metodę do klasy GuiCommands
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            int clickedSlot = event.getSlot();

            // Sprawdź, czy kliknięty slot to jeden z wymienionych
            if (clickedSlot == 10 || clickedSlot == 11 || clickedSlot == 12 || clickedSlot == 14 || clickedSlot == 15 || clickedSlot == 16) {
                player.sendMessage("Kliknąłeś na slot " + clickedSlot);
            }
        }
    }
}

