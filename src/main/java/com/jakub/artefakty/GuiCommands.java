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

    private createCustomItem customItemCreator;
    private getArtefaktyInventory artefaktyInventory;
    private Map<UUID, Map<String, Integer>> playerItems = new HashMap<>(); // Nowa mapa
    private Artefakty plugin;
    private UUID[] args;
    private String itemKey;

    private Map<String, String> itemNames = new HashMap<>();

    public GuiCommands(getArtefaktyInventory artefaktyInventory, Artefakty plugin, Rewards rewards) {
        this.artefaktyInventory = artefaktyInventory;
        this.customItemCreator = new createCustomItem();
        this.plugin = plugin;
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
            if (args.length > 3 && args[0].equals("admin") && args[1].equals("clear")) {
                String itemKey = args[2];
                Player targetPlayer = Bukkit.getPlayer(args[3]);
                if (targetPlayer != null) {
                    if (itemKey.equals("all")) {
                        for (String configItemName : itemNames.values()) {
                            String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                            if (plugin.getConfig().contains(playerKey)) {
                                plugin.getConfig().set(playerKey, null); // Usuń klucz z konfiguracji
                            }
                        }
                        player.sendMessage("§b§lSky§aMMO §cUsunięto wszystkie przedmioty graczu " + targetPlayer.getName());
                    } else {
                        String configItemName = itemNames.get(itemKey);
                        if (configItemName != null) {
                            String playerKey = targetPlayer.getUniqueId().toString() + "." + configItemName;
                            if (plugin.getConfig().contains(playerKey)) {
                                int currentItems = plugin.getConfig().getInt(playerKey, 1);
                                plugin.getConfig().set(playerKey, currentItems - 1); // Usuń klucz z konfiguracji
                                player.sendMessage("§b§lSky§aMMO §cUsunięto " + itemKey + " graczu " + targetPlayer.getName());
                            } else {
                                player.sendMessage("§b§lSky§aMMO §cNie udało się usunąć przedmiotu " + itemKey + ", ponieważ nie istnieje w konfiguracji.");
                            }
                        } else {
                            player.sendMessage("§b§lSky§aMMO §cNieznany przedmiot: " + itemKey);
                        }
                    }
                    plugin.saveConfig(); // Zapisz konfigurację
                } else {
                    player.sendMessage("§b§lSky§aMMO §cNie znaleziono gracza " + args[3]);
                }
            } else {
                player.sendMessage("§b§lSky§aMMO §dOtwieram trofea...");
                player.openInventory(artefaktyInventory.getArtefaktyInventory(1, 2, 3, 4, 5, 6));
            }
        }
        return true;
    }










    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("admin", "clear"); // Możliwe argumenty dla pierwszego argumentu
        } else if (args.length == 2 && args[0].equals("admin")) {
            return Collections.singletonList("clear"); // Możliwe argumenty dla drugiego argumentu, jeśli pierwszy to "admin"
        } else if (args.length == 3 && args[0].equals("admin") && args[1].equals("clear")) {
            // Możliwe argumenty dla trzeciego argumentu, jeśli pierwszy to "admin" a drugi to "clear"
            // Zwróć listę wszystkich możliwych przedmiotów do usunięcia
            return Arrays.asList("rogMino", "slepiePradawnego", "berloKrola", "klepsydraReaper", "kosaNiebieskiej", "substancjaKsiecia", "all");
        } else if (args.length == 4 && args[0].equals("admin") && args[1].equals("clear")) {
            // Możliwe argumenty dla czwartego argumentu, jeśli pierwsze dwa to "admin" i "clear"
            // Zwróć listę wszystkich graczy online
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        }
        return null;
    }




}
