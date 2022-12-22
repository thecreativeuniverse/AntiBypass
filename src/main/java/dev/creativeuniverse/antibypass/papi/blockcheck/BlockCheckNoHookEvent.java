package dev.creativeuniverse.antibypass.papi.blockcheck;

import dev.creativeuniverse.antibypass.AntiBypass;
import dev.creativeuniverse.antibypass.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventException;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class BlockCheckNoHookEvent extends BlockCheck {

    private final boolean isPlace;
    private final Set<String> pluginsBlacklist = new HashSet<>();
    private final Set<String> classesBlacklist = new HashSet<>();
    private final Set<String> pluginsWhitelist = new HashSet<>();
    private final Set<String> classesWhitelist = new HashSet<>();

    public BlockCheckNoHookEvent(AntiBypass plugin) {
        super(plugin);
        ConfigurationSection config = plugin.getConfig();
        this.isPlace = config.getString(Config.BlockCheck.BlockEvent.TYPE, Config.BlockCheck.BlockEvent.Type.BREAK).equals(Config.BlockCheck.BlockEvent.Type.PLACE);

        if (config.contains(Config.BlockCheck.BlockEvent.BlackList.PLUGINS)) {
            pluginsBlacklist.addAll(config.getStringList(Config.BlockCheck.BlockEvent.BlackList.PLUGINS));
        }
        if (config.contains(Config.BlockCheck.BlockEvent.BlackList.CLASS)) {
            classesBlacklist.addAll(config.getStringList(Config.BlockCheck.BlockEvent.BlackList.CLASS));
        }
        if (config.contains(Config.BlockCheck.BlockEvent.Whitelist.PLUGINS)) {
            pluginsWhitelist.addAll(config.getStringList(Config.BlockCheck.BlockEvent.Whitelist.PLUGINS));
        }
        if (config.contains(Config.BlockCheck.BlockEvent.Whitelist.CLASS)) {
            classesWhitelist.addAll(config.getStringList(Config.BlockCheck.BlockEvent.Whitelist.CLASS));
        }
    }

    // %blockcheck_x=<x>;y=<y>;z=<z>;world=<world_name>%
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Location blockLoc = getLocation(params);

        Block block = blockLoc.getBlock();
        BlockEvent dummyEvent;
        if (isPlace) {
            dummyEvent = new BlockPlaceEvent(block, block.getState(), block.getRelative(BlockFace.DOWN), new ItemStack(Material.AIR), player, true, EquipmentSlot.HAND);
        } else {
            dummyEvent = new BlockBreakEvent(block, player);
        }

        if (skipChecks()) {
            Bukkit.getServer().getPluginManager().callEvent(dummyEvent);
            return String.valueOf(!((Cancellable) dummyEvent).isCancelled());
        } else {
            RegisteredListener[] listeners = dummyEvent.getHandlers().getRegisteredListeners();
            for (RegisteredListener rl : listeners) {
                String className = rl.getListener().getClass().getName();
                String pluginName = rl.getPlugin().getName();
                if (check(className, pluginName)) {
                    try {
                        rl.callEvent(dummyEvent);
                        if (((Cancellable) dummyEvent).isCancelled()) return String.valueOf(false);
                    } catch (EventException e) {
                        e.printStackTrace();
                    }
                }
            }
            return String.valueOf(true);
        }
    }

    private boolean skipChecks() {
        return classesWhitelist.isEmpty() && classesBlacklist.isEmpty() && pluginsWhitelist.isEmpty() && pluginsBlacklist.isEmpty();
    }

    private boolean check(String className, String pluginName) {
        return (!classesWhitelist.isEmpty() && classesWhitelist.contains(className))
               || ((!pluginsWhitelist.isEmpty() && pluginsWhitelist.contains(pluginName))
                   && (!classesBlacklist.isEmpty() && !classesBlacklist.contains(className))
                   && (!pluginsBlacklist.isEmpty() && !pluginsBlacklist.contains(pluginName)));
    }

}
