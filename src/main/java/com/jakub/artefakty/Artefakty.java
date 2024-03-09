package com.jakub.artefakty;

import mc.thelblack.custominventory.CInventoryManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Artefakty extends JavaPlugin {

    static CInventoryManager inventoryManager;
    private getArtefaktyInventory artefaktyInventory;

    @Override
    public void onEnable() {
        inventoryManager = new CInventoryManager(this);
        artefaktyInventory = new getArtefaktyInventory();
        getCommand("trofeum").setExecutor(new GuiCommands(artefaktyInventory));
        getCommand("trofea").setExecutor(new GuiCommands(artefaktyInventory));
        getServer().getPluginManager().registerEvents((Listener) new GuiCommands(artefaktyInventory), (Plugin) this);
    }

    @Override
    public void onDisable() {
        // Kod do wykonania podczas wyłączania pluginu
    }
}
