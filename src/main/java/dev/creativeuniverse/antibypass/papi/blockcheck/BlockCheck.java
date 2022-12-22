package dev.creativeuniverse.antibypass.papi.blockcheck;

import dev.creativeuniverse.antibypass.AntiBypass;
import dev.creativeuniverse.antibypass.papi.AntiBypassPAPI;
import dev.creativeuniverse.antibypass.papi.Params;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public abstract class BlockCheck extends AntiBypassPAPI {

    public BlockCheck(AntiBypass plugin) {
        super(plugin);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "blockcheck";
    }

    // %blockcheck_x=<x>;y=<y>;z=<z>;world=<world_name>%
    protected Location getLocation(String rawParams) {

        Params params = getParams(rawParams);

        World world = Bukkit.getWorld(params.get("world"));
        if (world == null) {
            throw new IllegalStateException("Could not parse args " + params + ": World " + params.get("world") + " does not exist.");
        }
        try {
            float x = Float.parseFloat(params.get("x"));
            float y = Float.parseFloat(params.get("y"));
            float z = Float.parseFloat(params.get("z"));
            return new Location(world, x, y, z);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Could not parse args " + rawParams + ": x, y, and/or z are not numbers.");
        }
    }

}
