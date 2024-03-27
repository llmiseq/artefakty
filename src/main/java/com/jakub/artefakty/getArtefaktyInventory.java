package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class getArtefaktyInventory {
    private static final ItemStack FILL_BLACK;

    public static YamlData yamlData = new YamlData("Artefakty.yml");

    static {
        FILL_BLACK = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = FILL_BLACK.getItemMeta();
        meta.setDisplayName(" ");
        FILL_BLACK.setItemMeta(meta);
    }

    private String artefactKey;

    public Inventory getArtefaktyInventory() {
        System.out.println("Wywołano metodę getArtefaktyInventory");
        return Artefakty.inventoryManager.builder().setTitle("§6Twoje Trofea").setRows(3).fill(FILL_BLACK)
                .addEventInventoryOpen((p, e) -> {
                    System.out.println("Wywołano zdarzenie otwarcia ekwipunku dla gracza: " + p.getName());
                    InventoryInit.artefaktModels.forEach(artefaktModel -> {
                        System.out.println("Przetwarzam model artefaktu: " + artefaktModel);
                        ItemStack item = artefaktModel.getItemStack().clone(); // Klonuj ItemStack, aby uniknąć zmiany oryginalnego modelu
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(artefaktModel.getName()); // Ustaw nazwę wyświetlaną na wartość z pliku konfiguracyjnego
                        item.setItemMeta(meta);
                        e.getInventory().setItem(artefaktModel.getSlotInEq(), item);
                    });
                })
                .addEventInventoryClick((p, e) -> {
                    System.out.println("Wywołano zdarzenie kliknięcia w ekwipunek dla gracza: " + p.getName());
                    if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;
                    e.setCancelled(true); // Anuluj zdarzenie, aby zapobiec wyciągnięciu przedmiotu
                    if (e.getClickedInventory().getType() != InventoryType.CHEST || e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) return; // Dodano sprawdzenie, czy kliknięty przedmiot znajduje się w GUI i czy nie jest czarnym szkłem
                    if (e.getCurrentItem().isSimilar(FILL_BLACK)) return; // Dodano sprawdzenie, czy kliknięty przedmiot to czarne szkło

                    ItemStack clickedItem = e.getCurrentItem();

                    // Wywołaj metodę handlePlayerInteraction
                    System.out.println("Kliknięto przedmiot: " + clickedItem);
                    handlePlayerInteraction(p, clickedItem);
                })
                .build();
    }

    public void handlePlayerInteraction(Player player, ItemStack clickedItem) {
        System.out.println("Wywołano metodę handlePlayerInteraction dla gracza: " + player.getName());

        // Utwórz klucz dla PersistentDataContainer
        NamespacedKey key = new NamespacedKey(Artefakty.getInstance(), "artefactKey");

        // Pobierz PersistentDataContainer z ItemStack
        PersistentDataContainer data = clickedItem.getItemMeta().getPersistentDataContainer();

        // Pobierz wartość z PersistentDataContainer
        String artefactKey = data.get(key, PersistentDataType.STRING);
        System.out.println("Klucz artefaktu: " + artefactKey);

        String playerKey = player.getUniqueId().toString() + "." + artefactKey;
        System.out.println("Klucz gracza: " + playerKey);

        // Sprawdź, czy gracz ma w ekwipunku przynajmniej jeden przedmiot o tym samym typie i nazwie
        boolean hasItem = Arrays.stream(player.getInventory().getContents())
                .anyMatch(item -> item != null && item.getType() == clickedItem.getType() && item.getItemMeta().getDisplayName().equals(clickedItem.getItemMeta().getDisplayName()));

        if (!hasItem) {
            player.sendMessage("§b§lSky§aMMO §cNie posiadasz żadnych takich przedmiotów!");
            return;
        }

        // Usuń przedmiot z ekwipunku gracza
        boolean removed = removeItem(player, clickedItem);
        if (removed) {
            int currentItems = Artefakty.getInstance().getConfig().getInt(playerKey, 0);
            Artefakty.getInstance().getConfig().set(playerKey, Math.max(0, currentItems - 1)); // Aktualizuj konfigurację
            System.out.println("Zaktualizowano konfigurację dla " + playerKey + " na " + Math.max(0, currentItems - 1));

            Artefakty.getInstance().saveConfig(); // Zapisz konfigurację
            System.out.println("Zapisano konfigurację");

            player.sendMessage("§b§lSky§aMMO §eUsunięto przedmiot: " + clickedItem.getItemMeta().getDisplayName());

            // Dodaj przedmiot do konfiguracji
            String configItemName = artefactKey;
            playerKey = player.getUniqueId().toString() + "." + configItemName;
            currentItems = Artefakty.getInstance().getConfig().getInt(playerKey, 0);
            if (currentItems < 1) {
                Artefakty.getInstance().getConfig().set(playerKey, currentItems + 1);
                player.sendMessage("§b§lSky§aMMO §cDodano " + artefactKey + " graczu " + player.getName());
            } else {
                player.sendMessage("§b§lSky§aMMO §cNie można dodać więcej przedmiotów " + artefactKey + ", ponieważ osiągnięto limit.");
            }
        } else {
            player.sendMessage("§b§lSky§aMMO §cNie udało się usunąć przedmiotu!");
        }
    }


    public boolean removeItem(Player player, ItemStack item) {
        System.out.println("Wywołano metodę removeItem dla gracza: " + player.getName());

        Inventory inventory = player.getInventory();
        for (ItemStack invItem : inventory.getContents()) {
            if (invItem != null && invItem.getType() == item.getType() && invItem.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                invItem.setAmount(invItem.getAmount() - 1);
                System.out.println("Zmniejszono ilość przedmiotów: " + invItem.getItemMeta().getDisplayName() + " do " + invItem.getAmount()); // Dodano log

                if (invItem.getAmount() <= 0) {
                    inventory.remove(invItem);
                }
                System.out.println("Usunięto przedmiot: " + invItem.getItemMeta().getDisplayName());
                return true;
            }
        }
        return false;
    }
}
