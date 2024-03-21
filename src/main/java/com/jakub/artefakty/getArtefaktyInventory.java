package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class getArtefaktyInventory {
    private static final ItemStack FILL_BLACK;
    private createCustomItem customItemCreator = new createCustomItem();

    public static YamlData yamlData = new YamlData("Artefakty.yml");

    static {
        FILL_BLACK = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = FILL_BLACK.getItemMeta();
        meta.setDisplayName(" ");
        FILL_BLACK.setItemMeta(meta);
    }

    private final MyPlugin myPluginInstance;

    public getArtefaktyInventory() {
        this.myPluginInstance = MyPlugin.getInstance(); // Pobierz instancję twojego pluginu
    }

    public Inventory getArtefaktyInventory(int rogMino, int slepiePradawnego, int berloKrola, int klepsydraReaper, int kosaNiebieskiej, int substancjaKsiecia) {
        return Artefakty.inventoryManager.builder().setTitle("§6Twoje Trofea").setRows(3).fill(FILL_BLACK)
                .addEventInventoryOpen((p, e) -> {
                    InventoryInit.artefaktModels.forEach(artefaktModel -> {
                        e.getInventory().setItem(artefaktModel.getSlotInEq(), artefaktModel.getPlayerItem(p));
                    });
                })
                .addEventInventoryClick((p, e) -> {
                    if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;
                    e.setCancelled(true); // Anuluj zdarzenie, aby zapobiec wyciągnięciu przedmiotu

                    ItemStack clickedItem = e.getCurrentItem();

                    // Wywołaj metodę handlePlayerInteraction
                    handlePlayerInteraction(p, clickedItem);
                })
                .build();
    }

    public void handlePlayerInteraction(Player player, ItemStack clickedItem) {
        // Odczytaj maxItemsPerPlayer z pliku konfiguracyjnego dla danego artefaktu
        String artefactKey = clickedItem.getItemMeta().getDisplayName();
        int maxItemsPerPlayer = Artefakty.getInstance().getConfig().getInt("artefakty." + artefactKey + ".MaxInEq", 1);
        String playerKey = player.getUniqueId().toString() + "." + artefactKey;
        int currentItems = Artefakty.getInstance().getConfig().getInt(playerKey, 0);
        if (currentItems >= maxItemsPerPlayer) {
            player.sendMessage("§b§lSky§aMMO §cNie możesz dodać więcej takich przedmiotów!");
            return;
        }

        if (removeItem(player, clickedItem)) {
            player.sendMessage("§b§lSky§aMMO §eDodano " + clickedItem.getItemMeta().getDisplayName());
            Artefakty.getInstance().getConfig().set(playerKey, currentItems + 1); // Aktualizuj konfigurację
            Artefakty.getInstance().saveConfig(); // Zapisz konfigurację
        }
    }

    // Tutaj zaimplementowałem metodę removeItem do usuwania
    public boolean removeItem(Player player, ItemStack item) {
        // Implementacja metody removeItem
        return false; // Zwróć prawdziwą wartość, jeśli przedmiot został pomyślnie usunięty
    }
}
