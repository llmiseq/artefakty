package com.jakub.artefakty;

import mc.thelblack.custominventory.CInventoryManager;
import org.bukkit.plugin.java.JavaPlugin;
public final class Artefakty extends JavaPlugin {

    static CInventoryManager inventoryManager;

    @Override
    public void onEnable() {
        inventoryManager = new CInventoryManager(this);
        getCommand("trofeum").setExecutor(new GuiCommands());
    }

    @Override
    public void onDisable() {


    }
}
