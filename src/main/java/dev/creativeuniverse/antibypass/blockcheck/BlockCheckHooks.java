package dev.creativeuniverse.antibypass.blockcheck;

import dev.creativeuniverse.antibypass.AntiBypass;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockCheckHooks extends BlockCheck {

    public BlockCheckHooks(AntiBypass plugin) {
        super(plugin);
    }

    // %blockcheck_world_x_y_z%
    //TODO
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Location blockLoc = getLocation(params);

        Block block = blockLoc.getBlock();

        return "false";
    }


}
