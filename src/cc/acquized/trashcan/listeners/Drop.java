package cc.acquized.trashcan.listeners;

import cc.acquized.trashcan.Main;
import cc.acquized.trashcan.provider.ConfigProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;

public class Drop implements Listener {

    private Material material;
    private boolean in;
    private boolean above;
    private List<World> disabledWorlds;

    public Drop() {
        try {
            material = Material.valueOf(ConfigProvider.getConfiguration().getString("Trashcan.Type").toUpperCase());
        } catch (IllegalArgumentException ex) {
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Unknown Type: " + ConfigProvider.getConfiguration().getString("Trashcan.Type").toUpperCase());
            Bukkit.getConsoleSender().sendMessage("[Trashcan] Automaticlly set the Material to Cauldron.");
            material = Material.CAULDRON;
        }
        in = ConfigProvider.getConfiguration().getBoolean("DroppedItem.InCauldron");
        above = ConfigProvider.getConfiguration().getBoolean("DroppedItem.AboveCauldron");
        for(String s : ConfigProvider.getConfiguration().getStringList("DisabledWorlds")) {
            if(Bukkit.getWorld(s) != null) {
                disabledWorlds.add(Bukkit.getWorld(s));
            }
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                for(World w : Bukkit.getWorlds()) {
                    if(disabledWorlds.contains(w)) {
                        return;
                    }
                    for(Entity ent : w.getEntities()) {
                        if(ent instanceof Item) {
                            Item item = (Item) ent;
                            Location aboveBlock = item.getLocation();
                            Location inBlock = item.getLocation().subtract(0, 1, 0);
                            if((w.getBlockAt(aboveBlock).getType() == material) && (above)) {
                                item.remove();
                            }
                            if((w.getBlockAt(inBlock).getType() == material) && (in)) {
                                item.remove();
                            }
                        }
                    }
                }
            }
        }, 0, 20);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        Location aboveBlock = e.getItemDrop().getLocation();
        Location inBlock = e.getItemDrop().getLocation().subtract(0, 1, 0);
        if((p.getWorld().getBlockAt(aboveBlock).getType() == material) && (above)) {
            e.getItemDrop().remove();
        }
        if((p.getWorld().getBlockAt(inBlock).getType() == material) && (in)) {
            e.getItemDrop().remove();
        }
    }

}
