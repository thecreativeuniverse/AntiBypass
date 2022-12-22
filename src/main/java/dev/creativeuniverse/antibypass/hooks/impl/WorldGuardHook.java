package dev.creativeuniverse.antibypass.hooks.impl;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import dev.creativeuniverse.antibypass.hooks.BuildHook;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WorldGuardHook implements BuildHook {

    @Override
    public boolean check(Player player, Location loc) {
        com.sk89q.worldedit.util.Location wgLoc = new com.sk89q.worldedit.util.Location(new BukkitWorld(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ());
        LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        return query.testState(wgLoc, localPlayer, Flags.BUILD);
    }

}
