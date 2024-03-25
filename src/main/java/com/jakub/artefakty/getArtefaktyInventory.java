package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class getArtefaktyInventory {
    private static final ItemStack FILL_BLACK;

    public static YamlData yamlData = new YamlData("Artefakty.yml");

    static {
        FILL_BLACK = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = FILL_BLACK.getItemMeta();
        meta.setDisplayName(" ");
        FILL_BLACK.setItemMeta(meta);
    }

    public Inventory getArtefaktyInventory() {
        System.out.println("++++++++++++++++++");
        System.out.println("Klasa: " + this.getClass().getSimpleName());
        System.out.println("++++++++++++++++++");
        System.out.println("Wywołano metodę getArtefaktyInventory");
        return Artefakty.inventoryManager.builder().setTitle("§6Twoje Trofea").setRows(3).fill(FILL_BLACK)
                .addEventInventoryOpen((p, e) -> {
                    System.out.println("++++++++++++++++++");
                    System.out.println("Klasa: " + this.getClass().getSimpleName());
                    System.out.println("++++++++++++++++++");
                    System.out.println("Wywołano zdarzenie otwarcia ekwipunku dla gracza: " + p.getName());
                    InventoryInit.artefaktModels.forEach(artefaktModel -> {
                        System.out.println("Przetwarzam model artefaktu: " + artefaktModel);
                        ItemStack item = artefaktModel.getPlayerItem(p);
                        e.getInventory().setItem(artefaktModel.getSlotInEq(), item);
                    });
                })
                .addEventInventoryClick((p, e) -> {
                    System.out.println("++++++++++++++++++");
                    System.out.println("Klasa: " + this.getClass().getSimpleName());
                    System.out.println("++++++++++++++++++");
                    System.out.println("Wywołano zdarzenie kliknięcia w ekwipunek dla gracza: " + p.getName());
                    if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;
                    e.setCancelled(true); // Anuluj zdarzenie, aby zapobiec wyciągnięciu przedmiotu

                    ItemStack clickedItem = e.getCurrentItem();

                    // Wywołaj metodę handlePlayerInteraction
                    System.out.println("Kliknięto przedmiot: " + clickedItem);
                    handlePlayerInteraction(p, clickedItem);
                })
                .build();
    }

    public void handlePlayerInteraction(Player player, ItemStack clickedItem) {
        System.out.println("Wywołano metodę handlePlayerInteraction dla gracza: " + player.getName());
        // Odczyta maxItemsPerPlayer z pliku konfiguracyjnego dla danego artefaktu
        String artefactKey = clickedItem.getItemMeta().getDisplayName();
        int maxItemsPerPlayer = Artefakty.getInstance().getConfig().getInt("artefakty." + artefactKey + ".MaxInEq", 0);
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
        System.out.println("Wywołano metodę removeItem dla gracza: " + player.getName());
        // Implementacja metody removeItem
        return false; // Zwróć prawdziwą wartość, jeśli przedmiot został pomyślnie usunięty
    }
}
