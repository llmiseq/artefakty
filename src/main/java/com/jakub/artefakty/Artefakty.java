package com.jakub.artefakty;

import mc.thelblack.custominventory.CInventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Artefakty extends JavaPlugin {

    static CInventoryManager inventoryManager;
    private getArtefaktyInventory artefaktyInventory;

    @Override
    public void onEnable() {
        inventoryManager = new CInventoryManager(this);
        artefaktyInventory = new getArtefaktyInventory();
        getCommand("trofeum").setExecutor(new GuiCommands(artefaktyInventory));
    }

    @Override
    public void onDisable() {
        // Kod do wykonania podczas wyłączania pluginu
    }
}
