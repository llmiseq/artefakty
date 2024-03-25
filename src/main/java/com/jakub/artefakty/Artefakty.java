package com.jakub.artefakty;

import mc.thelblack.custominventory.CInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class Artefakty extends JavaPlugin implements Listener{

    static CInventoryManager inventoryManager;
    private getArtefaktyInventory artefaktyInventory;
    private Rewards rewards;

    private static Artefakty artefakty;

    public static Artefakty getInstance(){
        return artefakty;
    }

    @Override
    public void onEnable() {
        artefakty = this;
        inventoryManager = new CInventoryManager(this);
        artefaktyInventory = new getArtefaktyInventory();
        rewards = new Rewards(this);

        Artefakty.getInstance().reloadConfig(); // Dodane przeładowanie konfiguracji
        loadArtefaktsFromConfig(); // Dodane wczytywanie artefaktów z konfiguracji
        InventoryInit.loadArtefaktModels();

        GuiCommands guiCommands = new GuiCommands(artefaktyInventory, this, rewards);
        getCommand("trofea").setExecutor(guiCommands);
        getCommand("trofea").setTabCompleter(guiCommands);
        getCommand("trofeum").setExecutor(guiCommands);
        getCommand("trofeum").setTabCompleter(guiCommands);
        getServer().getPluginManager().registerEvents(guiCommands, this);

        // Uruchomienie zadania zaplanowanego co minutę
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                // Wywołanie metody checkAndReward dla każdego gracza online
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    rewards.checkAndReward(player);
                }
            }
        }, 0L, 1200L); // 1200 ticków to około 1 minuta

        System.out.println("Metoda onEnable została wywołana!"); // Dodane logowanie
    }

    public void loadArtefaktsFromConfig() {
        System.out.println("Wywołano metodę loadArtefaktsFromConfig");
        FileConfiguration config = Artefakty.getInstance().getConfig();
        ConfigurationSection artefaktySection = config.getConfigurationSection("artefakty");
        if (artefaktySection == null) return;

        InventoryInit.artefaktModels.clear(); // Dodane czyszczenie listy

        for (String key : artefaktySection.getKeys(false)) {
            System.out.println("Przetwarzam klucz: " + key);
            ArtefaktModel artefaktModel = new ArtefaktModel();
            artefaktModel.setItemStack(new ItemStack(Material.valueOf(config.getString("artefakty." + key + ".ItemStack"))));
            artefaktModel.setSlotInEq(config.getInt("artefakty." + key + ".SlotInEq"));
            artefaktModel.setMaxInEq(config.getInt("artefakty." + key + ".MaxInEq"));
            artefaktModel.setBonuses(config.getStringList("artefakty." + key + ".Bonuses"));

            InventoryInit.artefaktModels.add(artefaktModel);
            System.out.println("Dodano model artefaktu: " + artefaktModel);
        }
    }
}
