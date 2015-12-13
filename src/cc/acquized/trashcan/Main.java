package cc.acquized.trashcan;

import cc.acquized.trashcan.listeners.Drop;
import cc.acquized.trashcan.listeners.Interact;
import cc.acquized.trashcan.provider.ConfigProvider;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        ConfigProvider.checkFile();
        registerListeners();
        Bukkit.getConsoleSender().sendMessage("[Trashcan] Trashcan v" + getDescription().getVersion() + " was enabled.");
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getConsoleSender().sendMessage("[Trashcan] Trashcan v" + getDescription().getVersion() + " was disbled.");
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new Drop(), this);
        pm.registerEvents(new Interact(), this);
    }

    public static Main getInstance() {
        return instance;
    }

}
