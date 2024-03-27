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
    private Rewards rewards;

    public Map<String, String> itemNames = new HashMap<>();

    public GuiCommands(getArtefaktyInventory artefaktyInventory, Artefakty plugin, Rewards rewards) {
        this.artefaktyInventory = artefaktyInventory;
        this.plugin = plugin;
        this.rewards = rewards;
        itemNames.put("rogMino", "§2Róg Minotaura");
        itemNames.put("slepiePradawnego", "§6Wyrwane Ślepie Pradawnego");
        itemNames.put("berloKrola", "§3Berło Króla Północy");
        itemNames.put("klepsydraReaper", "§5Klepsydra z Piaskiem Życia");
        itemNames.put("kosaNiebieskiej", "§5Niebieska Kosa 3000");
        itemNames.put("substancjaKsiecia", "§4Fałszywy Flogiston");
        itemNames.put("all", "Wszystkie przedmioty");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length > 3 && args[0].equals("admin")) {
                String ID = args[2]; // Zmień nazwę zmiennej z itemKey na ID
                Player targetPlayer = Bukkit.getPlayer(args[3]);
                if (targetPlayer == null) {
                    player.sendMessage("§b§lSky§aMMO §cNie znaleziono gracza " + args[3]);
                    return true;
                }
                if (args[1].equals("add")) {
                    if (ID.equals("all")) {
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
                        String configItemName = itemNames.get(ID);
                        if (configItemName == null) {
                            player.sendMessage("§b§lSky§aMMO §cNieznany przedmiot: " + ID);
                            return true;
                        }
                        String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                        int currentItems = plugin.getConfig().getInt(playerKey, 0);
                        if (currentItems >= 1) {
                            player.sendMessage("§b§lSky§aMMO §cNie można dodać więcej przedmiotów " + ID + ", ponieważ osiągnięto limit.");
                            return true;
                        }
                        plugin.getConfig().set(playerKey, currentItems + 1);
                        player.sendMessage("§b§lSky§aMMO §cDodano " + ID + " graczu " + targetPlayer.getName());
                    }

                } else if (args[1].equals("clear")) {
                    if (ID.equals("all")) {
                        for (String key : itemNames.keySet()) {
                            String configItemName = itemNames.get(key);
                            String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                            if (plugin.getConfig().contains(playerKey)) {
                                plugin.getConfig().set(playerKey, 0);
                                player.sendMessage("§b§lSky§aMMO §cUsunięto " + key + " graczu " + targetPlayer.getName());
                            }
                        }
                    } else {
                        String configItemName = itemNames.get(ID);
                        if (configItemName == null) {
                            player.sendMessage("§b§lSky§aMMO §cNieznany przedmiot: " + ID);
                            return true;
                        }
                        String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                        if (!plugin.getConfig().contains(playerKey)) {
                            player.sendMessage("§b§lSky§aMMO §cNie udało się usunąć przedmiotu " + ID + ", ponieważ nie istnieje w konfiguracji.");
                            return true;
                        }
                        plugin.getConfig().set(playerKey, 0);
                        player.sendMessage("§b§lSky§aMMO §cUsunięto " + ID + " graczu " + targetPlayer.getName());
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
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("admin");
        } else if (args.length == 2) {
            return Arrays.asList("add", "clear");
        } else if (args.length == 3) {
            return new ArrayList<>(itemNames.keySet());
        } else if (args.length == 4) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        return null;
    }





}
