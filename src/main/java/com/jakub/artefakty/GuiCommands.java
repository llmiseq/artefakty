package com.jakub.artefakty;

import com.destroystokyo.paper.event.block.BeaconEffectEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuiCommands implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
             Player player = (Player) commandSender;
             player.sendMessage("§aOtwieram trofea...");

        }
        return false;
    }
}
