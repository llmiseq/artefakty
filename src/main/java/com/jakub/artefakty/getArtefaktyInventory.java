package com.jakub.artefakty;

import jdk.internal.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static jdk.jfr.internal.SecuritySupport.registerEvent;

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
    private Class<? extends Event> player;

    public Inventory getArtefaktyInventory(int rogMino, int slepiePradawnego, int berloKrola, int klepsydraReaper, int kosaNiebieskiej, int substancjaKsiecia) {
        return Artefakty.inventoryManager.builder().setTitle("§6Twoje Trofea").setRows(3).fill(FILL_BLACK).addEventInventoryOpen((p, e) -> {
                    //10 11 12 14 15 16
                    InventoryInit.artefaktModels.forEach(artefaktModel -> {
                        e.getInventory().setItem(artefaktModel.getSlotInEq(), artefaktModel.getPlayerItem(p));
                    });

                    //  e.getInventory().setItem(10, ART_MINO(p, rogMino));
                    //  e.getInventory().setItem(11, ART_PRAD(p, slepiePradawnego));
                    //  e.getInventory().setItem(12, ART_LODO(p, berloKrola));
                    //  e.getInventory().setItem(14, ART_DEM1(p, klepsydraReaper));
                    //  e.getInventory().setItem(15, ART_DEM2(p, kosaNiebieskiej));
                    //  e.getInventory().setItem(16, ART_ESKA(p, substancjaKsiecia));
                })
                .addEventInventoryClick((p, e) -> {
                    if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;
                    e.setCancelled(true); // Anuluj zdarzenie, aby zapobiec wyciągnięciu przedmiotu

                    int clickedSlot = e.getSlot();

                    ItemStack clickedItem = e.getCurrentItem();

                    //Sprawdzić który item został kliknięty i zmapować go do ArtefaktModel

                    public void checkAndRegisterEvent(Player player) {
                        MyPlugin myPluginInstance = MyPlugin.getInstance(); // Pobierz instancję twojego pluginu
                        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
                        NamespacedKey key = new NamespacedKey(myPluginInstance, "hasGuiOpen");

                        if (dataContainer.has(key, PersistentDataType.INTEGER)) {
                            int hasGuiOpen = dataContainer.get(key, PersistentDataType.INTEGER);
                            if (hasGuiOpen == 1) {
                                // Gracz ma otwarte GUI, rejestruj zdarzenie
                                handleGuiOpenEvent(player);
                            }
                        }
                    }



                    public void registerEvent(Player player) {
                        // Tworzymy nowy obiekt Listenera
                        Listener listener = new Listener() {
                            @EventHandler
                            public void onPlayerInteract(PlayerInteractEvent event) {
                                Player p = event.getPlayer();
                                ItemStack clickedItem = event.getItem(); // Pobierz kliknięty przedmiot

                                // Sprawdzamy, czy gracz, który wywołał zdarzenie, to ten sam gracz
                                if (p.equals(player)) {
                                    // Tutaj wykonujemy kod, który ma się wykonać po kliknięciu przez gracza

                                    // Sprawdź, czy gracz ma już maksymalną liczbę przedmiotów
                                    int maxItemsPerPlayer = 1;
                                    String playerKey = p.getUniqueId().toString() + "." + clickedItem.getItemMeta().getDisplayName();
                                    int currentItems = Artefakty.getInstance().getConfig().getInt(playerKey, 0);
                                    if (currentItems >= maxItemsPerPlayer) {
                                        p.sendMessage("§b§lSky§aMMO §cNie możesz dodać więcej takich przedmiotów!");
                                        return;
                                    }

                                    if (removeItem(p, clickedItem)) {
                                        p.sendMessage("§b§lSky§aMMO §eDodano " + clickedItem.getItemMeta().getDisplayName());
                                        Artefakty.getInstance().getConfig().set(playerKey, currentItems + 1); // Aktualizuj konfigurację
                                        Artefakty.getInstance().saveConfig(); // Zapisz konfigurację

                                        // Tworzymy nowy obiekt ArtefaktModel i mapujemy dane
                                        ArtefaktModel artefaktModel = new ArtefaktModel();
                                        artefaktModel.setItemStack(clickedItem);
                                        // Ustawiamy inne wartości w artefaktModel zgodnie z twoimi potrzebami
                                    } else {
                                        p.sendMessage("§b§lSky§aMMO §eBłąd, nie posiadasz przedmiotu " + clickedItem.getItemMeta().getDisplayName() + " w swoim ekwipunku");
                                    }
                                }
                            }

                            private boolean removeItem(Player player, ItemStack clickedItem) {
                                for (ItemStack item : player.getInventory().getContents()) {
                                    if (item != null && item.getType() == clickedItem.getType() && item.getItemMeta().getDisplayName().equals(clickedItem.getItemMeta().getDisplayName())) {
                                        if (item.getAmount() > 1) {
                                            item.setAmount(item.getAmount() - 1);
                                        } else {
                                            player.getInventory().remove(item);
                                        }
                                        player.updateInventory(); // Aktualizuj ekwipunek gracza
                                        return true;
                                    }
                                }
                                return false;
                            }

                        };

                        // Rejestrujemy  Listenera
                        @NotNull Plugin plugin = MyPlugin.getInstance(); // Pobierz instancję twojego pluginu
                        Bukkit.getPluginManager().registerEvents(listener, plugin);
                    }



                    //ItemRog(1)
                    // InventoryInit -> ItemRog
                    ArtefaktModel am;


//                    // Sprawdź, czy gracz ma już maksymalną liczbę przedmiotów
//                    int maxItemsPerPlayer = 1;
//                    String playerKey = p.getUniqueId().toString() + "." + clickedItem.getItemMeta().getDisplayName();
//                    int currentItems = Artefakty.getInstance().getConfig().getInt(playerKey, 0);
//                    if (currentItems >= maxItemsPerPlayer) {
//                        p.sendMessage("§b§lSky§aMMO §cNie możesz dodać więcej takich przedmiotów!");
//                        return;
//                    }
//
//                    if (removeItem(p, clickedItem)) {
//                        p.sendMessage("§b§lSky§aMMO §eDodano " + clickedItem.getItemMeta().getDisplayName());
//                        Artefakty.getInstance().getConfig().set(playerKey, currentItems + 1); // Aktualizuj konfigurację
//                        Artefakty.getInstance().saveConfig(); // Zapisz konfigurację
//                    } else {
//                        p.sendMessage("§b§lSky§aMMO §eBłąd, nie posiadasz przedmiotu " + clickedItem.getItemMeta().getDisplayName() + " w swoim ekwipunku");
//                    }


                }).build();

    }

    private void handleGuiOpenEvent(Class<? extends Event> player) {

    }

    private boolean removeItem(Player player, ItemStack clickedItem) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == clickedItem.getType() && item.getItemMeta().getDisplayName().equals(clickedItem.getItemMeta().getDisplayName())) {
                if (item.getAmount() > 1) {
                    item.setAmount(item.getAmount() - 1);
                } else {
                    player.getInventory().remove(item);
                }
                player.updateInventory(); // Aktualizuj ekwipunek gracza
                return true;
            }
        }
        return false;
    }

    private ItemStack ART_ESKA(Player p, int substancjaKsiecia) {
        return customItemCreator.item6;
    }

    private ItemStack ART_DEM2(Player p, int kosaNiebieskiej) {
        return customItemCreator.item5;
    }

    private ItemStack ART_DEM1(Player p, int klepsydraReapera) {
        return customItemCreator.item4;
    }

    private ItemStack ART_LODO(Player p, int berloKrola) {
        return customItemCreator.item3;
    }

    private ItemStack ART_PRAD(Player p, int slepiePradawnego) {
        return customItemCreator.item2;
    }

    private ItemStack ART_MINO(Player p, int rogMino) {
        return customItemCreator.item1;
    }
}
