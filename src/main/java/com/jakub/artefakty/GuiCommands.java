package com.jakub.artefakty;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuiCommands implements CommandExecutor {

    GuiCommands() {

    }
    private getArtefaktyInventory artefaktyInventory;

    public GuiCommands(getArtefaktyInventory artefaktyInventory) {
        this.artefaktyInventory = artefaktyInventory;
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
}
