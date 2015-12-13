package cc.acquized.trashcan.listeners;

import cc.acquized.trashcan.provider.ConfigProvider;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class Interact implements Listener {

    private Material material;
    private boolean interact;
    private String title;
    private List<World> disabledWorlds;

    public Interact() {
        try {
            material = Material.valueOf(ConfigProvider.getConfiguration().getString("Trashcan.Type").toUpperCase());
        } catch (IllegalArgumentException ex) {
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Unknown Type: " + ConfigProvider.getConfiguration().getString("Trashcan.Type").toUpperCase());
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Automaticlly set the Material to Cauldron.");
            material = Material.CAULDRON;
        }
        interact = ConfigProvider.getConfiguration().getBoolean("Inventory.Enabled");
        title = ChatColor.translateAlternateColorCodes('&', ConfigProvider.getConfiguration().getString("Inventory.Title"));
        for(String s : ConfigProvider.getConfiguration().getStringList("DisabledWorlds")) {
            if(Bukkit.getWorld(s) != null) {
                disabledWorlds.add(Bukkit.getWorld(s));
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if((e.getClickedBlock().getType() == material) && (interact) && (!p.isSneaking()) && (!disabledWorlds.contains(p.getWorld()))) {
                Inventory inv = Bukkit.createInventory(null, 18, title);
                p.openInventory(inv);
            }
        }
    }

}
