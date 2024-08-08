import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class WebPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.COBWEB) {
            placeWebsAround(block.getLocation());
        }
    }

    private void placeWebsAround(Location location) {
        int[][] offsets = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] offset : offsets) {
            Location loc = location.clone().add(offset[0], 0, offset[1]);
            loc.getBlock().setType(Material.COBWEB);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                location.getBlock().setType(Material.AIR);
                for (int[] offset : offsets) {
                    Location loc = location.clone().add(offset[0], 0, offset[1]);
                    if (loc.getBlock().getType() == Material.COBWEB) {
                        loc.getBlock().setType(Material.AIR);
                    }
                }
            }
        }.runTaskLater(WebPlugin.this, 20 * 15); // 20 ticks per second * 15 seconds
    }
}
