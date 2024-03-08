
package com.jakub.artefakty;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class getArtefaktyInventory {
    private static final ItemStack FILL_BLACK = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    public Inventory getArtefaktyInventory(int rogMino, int slepiePradawnego, int berloKrola, int klepsydraReaper, int kosaNiebieskiej, int substancjaKsiecia) {
        return Artefakty.inventoryManager.builder().setTitle("§6Twoje Trofea").setRows(3).fill(FILL_BLACK).addEventInventoryOpen((p, e) -> {
                    //10 11 12 14 15 16
                    e.getInventory().setItem(10 , ART_MINO(p , rogMino));
                    e.getInventory().setItem(11 , ART_PRAD(p , slepiePradawnego));
                    e.getInventory().setItem(12 , ART_LODO(p , berloKrola));
                    e.getInventory().setItem(14 , ART_DEM1(p , klepsydraReaper));
                    e.getInventory().setItem(15 , ART_DEM2(p , kosaNiebieskiej));
                    e.getInventory().setItem(16 , ART_ESKA(p , substancjaKsiecia));
                })
                .addEventInventoryClick((p, e) -> {
                    if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;



                    switch (e.getSlot()) {
                        case 10:
                            p.openInventory(getUpgradeInventory(true , rogMino));
                            break;
                        case 11:
                            p.openInventory(getUpgradeInventory(false , slepiePradawnego));
                            break;
                        case 12:
                            p.openInventory(getUpgradeInventory(false , berloKrola));
                            break;
                        case 14:
                            p.openInventory(getUpgradeInventory(false , klepsydraReaper));
                            break;
                        case 15:
                            p.openInventory(getUpgradeInventory(false , kosaNiebieskiej));
                            break;
                        case 16:
                            p.openInventory(getUpgradeInventory(false , substancjaKsiecia));
                            break;
                    }
                }).build();
    }


    private Inventory getUpgradeInventory(boolean b, int substancjaKsiecia) {
        return null;
    }

    private ItemStack ART_ESKA(Player p, int substancjaKsiecia) {
        return null;
    }

    private ItemStack ART_DEM2(Player p, int kosaNiebieskiej) {
        return null;
    }

    private ItemStack ART_DEM1(Player p, int klepsydraReapera) {
        return null;
    }

    private ItemStack ART_LODO(Player p, int berloKrola) {
        return null;
    }

    private ItemStack ART_PRAD(Player p, int slepiePradawnego) {
        return null;
    }

    private ItemStack ART_MINO(Player p, int rogMino) {
        return null;
    }
}
