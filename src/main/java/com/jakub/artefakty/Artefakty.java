package com.jakub.artefakty;

import mc.thelblack.custominventory.CInventoryManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Artefakty extends JavaPlugin {

    static CInventoryManager inventoryManager;
    private getArtefaktyInventory artefaktyInventory;

    @Override
    public void onEnable() {
        inventoryManager = new CInventoryManager(this);
        artefaktyInventory = new getArtefaktyInventory();
        GuiCommands guiCommands = new GuiCommands(artefaktyInventory, this); // Przekazuj instancję pluginu do GuiCommands
        getCommand("trofea").setExecutor(guiCommands);
        getCommand("trofea").setTabCompleter(guiCommands);
        getCommand("trofeum").setExecutor(guiCommands);
        getCommand("trofeum").setTabCompleter(guiCommands);
        getServer().getPluginManager().registerEvents(guiCommands, this);
    }
    @Override
    public void onDisable() {
        // Kod do wykonania podczas wyłączania pluginu
    }
}
