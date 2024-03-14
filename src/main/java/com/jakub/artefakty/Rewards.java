package com.jakub.artefakty;

import com.jakub.artefakty.Artefakty;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Rewards {
    private Artefakty plugin;
   // private FileConfiguration config;
    private Map<String, PotionEffectType> itemEffects = new HashMap<>();

    public Rewards(Artefakty plugin) {
        this.plugin = plugin;
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        //this.config = YamlConfiguration.loadConfiguration(configFile);

        // Dodajemy efekty do przedmiotów
        itemEffects.put("§2Róg Minotaura", PotionEffectType.INCREASE_DAMAGE);
        itemEffects.put("§6Wyrwane Ślepie Pradawnego", PotionEffectType.NIGHT_VISION);
        itemEffects.put("§3Berło Króla Północy", PotionEffectType.HUNGER);
        itemEffects.put("§5Klepsydra z Piaskiem Życia", PotionEffectType.DAMAGE_RESISTANCE);
        itemEffects.put("§5Niebieska Kosa 3000", PotionEffectType.REGENERATION);
        itemEffects.put("§4Fałszywy Flogiston", PotionEffectType.FIRE_RESISTANCE);
    }

    public void checkAndReward(Player player) {
        // Wczytujemy konfigurację za każdym razem, gdy metoda jest wywoływana
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        for (Map.Entry<String, PotionEffectType> entry : itemEffects.entrySet()) {
            String itemName = entry.getKey();
            PotionEffectType effect = entry.getValue();

            int itemCount = config.getInt(player.getUniqueId().toString() + "." + itemName, 0);
            if (itemCount > 0) {
                // Gracz posiada przedmiot, więc dodaj nagrodę
                player.addPotionEffect(new PotionEffect(effect, 600, 1));
                player.sendMessage("Dodano efekt " + effect.getName()); // Informuj gracza
            } else {
                // Gracz nie posiada przedmiotu, więc usuń go z konfiguracji
                config.set(player.getUniqueId().toString() + "." + itemName, null);
                player.sendMessage("Nie dodano efektu " + effect.getName()); // Informuj gracza
            }
        }


        try {
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
