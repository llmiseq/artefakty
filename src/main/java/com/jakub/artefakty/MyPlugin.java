package com.jakub.artefakty;

import org.bukkit.plugin.java.JavaPlugin;

public class MyPlugin extends JavaPlugin {
    private static MyPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // reszta twojego kodu
    }

    public static MyPlugin getInstance() {
        return instance;
    }
}

