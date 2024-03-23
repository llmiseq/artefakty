package com.jakub.artefakty;

import mc.thelblack.custominventory.CInventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Artefakty extends JavaPlugin {

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

        new InventoryInit();
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

        Artefakty.getInstance().reloadConfig(); // Dodane przeładowanie konfiguracji
    }

    @Override
    public void onDisable() {
        // Kod do wykonania podczas wyłączania pluginu
        System.out.println("Metoda onDisable została wywołana!"); // Dodane logowanie
    }
}
