package com.jakub.artefakty;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class createCustomItem {
    private ItemStack createCustomItem(Material material, String name, List<String> descriptions) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(descriptions);
        item.setItemMeta(meta);
        return item;
    }

    // Użyj metody createCustomItem do stworzenia przedmiotu z pięcioma linijkami opisu
    List<String> odpTakRog = Arrays.asList("§cPosiadane: §e§ltak", "§8Bonus: §65% wiecej zadawanych obrażeń");
    List<String> odpTakSlepie = Arrays.asList("§cPosiadane: §e§ltak", "§8Bonus: §65% więcej defensywy");
    List<String> odpTakBerlo = Arrays.asList("§cPosiadane: §e§ltak", "§8Bonus: §6stały efekt ogrzania");
    List<String> odpTakKlepsydra = Arrays.asList("§cPosiadane: §e§ltak", "§8Bonus: §65% więcej zdrowia");
    List<String> odpTakKosa = Arrays.asList("§cPosiadane: §e§ltak", "§8Bonus: §67% więcej szansy na kryta");
    List<String> odpTakFlogiston = Arrays.asList("§cPosiadane: §e§ltak", "§8Bonus: §6szansa na odporność na ogień");

    List<String> odpNie = Arrays.asList("§cPosiadane: §9§lnie", "§8Bonus: §0brak");

    ItemStack item1 = createCustomItem(Material.GREEN_DYE, "§2Róg Minotaura", odpTakRog);
    ItemStack item2 = createCustomItem(Material.GREEN_DYE, "§6Wyrwane Ślepie Pradawnego", odpNie);
    ItemStack item3 = createCustomItem(Material.GREEN_DYE, "§3Berło Króla Północy", odpNie);
    ItemStack item4 = createCustomItem(Material.GREEN_DYE, "§5Klepsydra z Piaskiem Życia", odpNie);
    ItemStack item5 = createCustomItem(Material.GREEN_DYE, "§5Niebieska Kosa 3000", odpNie);
    ItemStack item6 = createCustomItem(Material.GREEN_DYE, "§4Fałszywy Flogiston", odpTakFlogiston);
}
