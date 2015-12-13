package cc.acquized.trashcan.provider;

import cc.acquized.trashcan.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigProvider {

    private static File file = new File(Main.getInstance().getDataFolder(), "config.yml");
    private static FileConfiguration configuration;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void checkFile() {
        try {
            if (!new File(Main.getInstance().getDataFolder().toString()).exists()) {
                new File(Main.getInstance().getDataFolder().toString()).mkdir();
            }
            if (!file.exists()) {
                Files.copy(Main.getInstance().getResource("config.yml"), file.toPath());
            }
            configuration = YamlConfiguration.loadConfiguration(file);
        } catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Could not create \"config.yml\" File.");
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Please send the following Stacktrace to Acquized:");
            ex.printStackTrace();
        }
    }

    public static void saveFile() {
        try {
            configuration.save(file);
        } catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Could not save \"config.yml\" File.");
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Please send the following Stacktrace to Acquized:");
            ex.printStackTrace();
        }
    }

    public static File getFile() {
        return file;
    }

    public static FileConfiguration getConfiguration() {
        return configuration;
    }

}
