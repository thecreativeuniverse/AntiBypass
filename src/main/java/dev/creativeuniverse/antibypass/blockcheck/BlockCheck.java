package dev.creativeuniverse.antibypass.blockcheck;

import dev.creativeuniverse.antibypass.AntiBypass;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public abstract class BlockCheck extends PlaceholderExpansion {

    protected final AntiBypass plugin;

    public BlockCheck(AntiBypass plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "blockcheck";
    }

    @Override
    public @NotNull String getAuthor() {
        return "creativeuniverse";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    protected Location getLocation(String params) {
        System.out.println("parsing " + params); //debugging
        String[] args = params.split("_");
        if (args.length != 4) {
            throw new IllegalStateException("Could not parse args " + params + ": Expected 4, got " + args.length);
        }
        World world = Bukkit.getWorld(args[0]);
        if (world == null) {
            throw new IllegalStateException("Could not parse args " + params + ": World " + args[0] + " does not exist.");
        }
        try {
            float x = Float.parseFloat(args[1]);
            float y = Float.parseFloat(args[2]);
            float z = Float.parseFloat(args[3]);
            return new Location(world, x, y, z);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Could not parse args " + params + ": x, y, and/or z are not numbers.");
        }
    }

}
