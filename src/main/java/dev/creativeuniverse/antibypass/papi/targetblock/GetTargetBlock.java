package dev.creativeuniverse.antibypass.papi.targetblock;

import dev.creativeuniverse.antibypass.AntiBypass;
import dev.creativeuniverse.antibypass.papi.AntiBypassPAPI;
import dev.creativeuniverse.antibypass.papi.Params;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class GetTargetBlock extends AntiBypassPAPI {

    public GetTargetBlock(AntiBypass plugin) {
        super(plugin);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "gettargetblock";
    }

    /*
    %gettargetblock% -> returns player's target block, excluding fluids and within 5 blocks of the player
    
    Params:    
    - maxDistance:          max distance from player to target. Returns "NULL" if nothing is found within this distance.
                            Default: 5 
    - fluidMode:            whether to include water + lava.
                            Available options: ALWAYS, NEVER, SOURCE_ONLY
                            Default: NEVER.
    - defaultToPlayerLoc:   if no target block is found, should it return the player's location
                            Default: false
    
     */
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String rawParams) {
        int maxDist = 5;
        FluidCollisionMode mode = FluidCollisionMode.NEVER;

        Params params = getParams(rawParams);
        if (params.contains("maxdistance")) {
            try {
                maxDist = Integer.parseInt(params.get("maxdistance"));
            } catch (NumberFormatException e) {
                plugin.getLogger().warning(String.format("Could not parse %s as an integer for max distance.", params.get("maxDistance")));
            }
        }
        if (params.contains("fluidmode")) {
            try {
                mode = FluidCollisionMode.valueOf(params.get("fluidmode"));
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning(String.format("Could not parse %s as a fluid collision mode. Available options: %s",
                        params.get("fluidmode"),
                        String.join(", ", Arrays.stream(FluidCollisionMode.values()).map(FluidCollisionMode::toString).toArray(String[]::new)))
                );
            }
        }

        Block block = player.getTargetBlockExact(maxDist, mode);
        if (block != null) return toString(block.getLocation());
        else if (params.getOrDefault("defaultToPlayerLoc", "false").equalsIgnoreCase("true")) {
            return toString(player.getLocation());
        } else return "NULL";
    }

    private String toString(Location loc) {
        return String.format("x=%d;y=%d;z=%d;world=%s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName());
    }
}
