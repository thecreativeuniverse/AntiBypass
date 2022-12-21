package dev.creativeuniverse.antibypass.blockcheck;

import dev.creativeuniverse.antibypass.AntiBypass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockCheckNoHookEvent extends BlockCheck {

    public BlockCheckNoHookEvent(AntiBypass plugin) {
        super(plugin);
        //TODO: add blockBreak/blockPlace
    }

    // %blockcheck_world_x_y_z%
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Location blockLoc = getLocation(params);

        Block block = blockLoc.getBlock();
        BlockPlaceEvent dummyEvent = new BlockPlaceEvent(block, block.getState(), block.getRelative(BlockFace.DOWN), new ItemStack(Material.AIR), player, true, EquipmentSlot.HAND);
        Bukkit.getServer().getPluginManager().callEvent(dummyEvent);

        return String.valueOf(!dummyEvent.isCancelled());
    }

}
