package com.jakub.artefakty;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class GuiCommands implements CommandExecutor, Listener, TabCompleter {

    private getArtefaktyInventory artefaktyInventory;
    private Artefakty plugin;
    private Rewards rewards; // Dodaj to

    private Map<String, String> itemNames = new HashMap<>();

    public GuiCommands(getArtefaktyInventory artefaktyInventory, Artefakty plugin, Rewards rewards) {
        this.artefaktyInventory = artefaktyInventory;
        this.plugin = plugin;
        this.rewards = rewards; // Dodaj to
        itemNames.put("rogMino", "§2Róg Minotaura");
        itemNames.put("slepiePradawnego", "§6Wyrwane Ślepie Pradawnego");
        itemNames.put("berloKrola", "§3Berło Króla Północy");
        itemNames.put("klepsydraReaper", "§5Klepsydra z Piaskiem Życia");
        itemNames.put("kosaNiebieskiej", "§5Niebieska Kosa 3000");
        itemNames.put("substancjaKsiecia", "§4Fałszywy Flogiston");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length > 3 && args[0].equals("admin")) {
                String itemKey = args[2];
                Player targetPlayer = Bukkit.getPlayer(args[3]);
                if (targetPlayer == null) {
                    player.sendMessage("§b§lSky§aMMO §cNie znaleziono gracza " + args[3]);
                    return true;
                }
                if (args[1].equals("add")) {
                    if (itemKey.equals("all")) {
                        for (String key : itemNames.keySet()) {
                            String configItemName = itemNames.get(key);
                            String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                            int currentItems = plugin.getConfig().getInt(playerKey, 0);
                            if (currentItems < 1) {
                                plugin.getConfig().set(playerKey, currentItems + 1);
                                player.sendMessage("§b§lSky§aMMO §cDodano " + key + " graczu " + targetPlayer.getName());
                            } else {
                                player.sendMessage("§b§lSky§aMMO §cNie można dodać więcej przedmiotów " + key + ", ponieważ osiągnięto limit.");
                            }
                        }
                    } else {
                        String configItemName = itemNames.get(itemKey);
                        if (configItemName == null) {
                            player.sendMessage("§b§lSky§aMMO §cNieznany przedmiot: " + itemKey);
                            return true;
                        }
                        String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                        int currentItems = plugin.getConfig().getInt(playerKey, 0);
                        if (currentItems >= 1) {
                            player.sendMessage("§b§lSky§aMMO §cNie można dodać więcej przedmiotów " + itemKey + ", ponieważ osiągnięto limit.");
                            return true;
                        }
                        plugin.getConfig().set(playerKey, currentItems + 1);
                        player.sendMessage("§b§lSky§aMMO §cDodano " + itemKey + " graczu " + targetPlayer.getName());
                    }
                } else if (args[1].equals("clear")) {
                    if (itemKey.equals("all")) {
                        for (String key : itemNames.keySet()) {
                            String configItemName = itemNames.get(key);
                            String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                            if (plugin.getConfig().contains(playerKey)) {
                                plugin.getConfig().set(playerKey, 0);
                                player.sendMessage("§b§lSky§aMMO §cUsunięto " + key + " graczu " + targetPlayer.getName());
                            }
                        }
                    } else {
                        String configItemName = itemNames.get(itemKey);
                        if (configItemName == null) {
                            player.sendMessage("§b§lSky§aMMO §cNieznany przedmiot: " + itemKey);
                            return true;
                        }
                        String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                        if (!plugin.getConfig().contains(playerKey)) {
                            player.sendMessage("§b§lSky§aMMO §cNie udało się usunąć przedmiotu " + itemKey + ", ponieważ nie istnieje w konfiguracji.");
                            return true;
                        }
                        plugin.getConfig().set(playerKey, 0);
                        player.sendMessage("§b§lSky§aMMO §cUsunięto " + itemKey + " graczu " + targetPlayer.getName());
                    }
                }
                plugin.saveConfig();
            } else {
                player.sendMessage("§b§lSky§aMMO §dOtwieram trofea...");
                player.openInventory(artefaktyInventory.getArtefaktyInventory());
            }
        }
        return true;
    }






    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("admin"); // Możliwe argumenty dla pierwszego argumentu
        } else if (args.length == 2 && args[0].equals("admin")) {
            return Arrays.asList("add", "clear"); // Możliwe argumenty dla drugiego argumentu, jeśli pierwszy to "admin"
        } else if (args.length == 3 && args[0].equals("admin") && (args[1].equals("add") || args[1].equals("clear"))) {
            // Możliwe argumenty dla trzeciego argumentu, jeśli pierwszy to "admin" a drugi to "add" lub "clear"
            // Zwróć listę wszystkich możliwych przedmiotów do dodania/usunięcia
            return Arrays.asList("rogMino", "slepiePradawnego", "berloKrola", "klepsydraReaper", "kosaNiebieskiej", "substancjaKsiecia", "all");
        } else if (args.length == 4 && args[0].equals("admin") && (args[1].equals("add") || args[1].equals("clear"))) {
            // Możliwe argumenty dla czwartego argumentu, jeśli pierwsze dwa to "admin" i "add" lub "clear"
            // Zwróć listę wszystkich graczy online
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        return null;
    }



}
