package dev.creativeuniverse.antibypass.hooks.impl;

import dev.creativeuniverse.antibypass.hooks.BuildHook;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.ClaimPermission;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GriefPreventionHook implements BuildHook {

    @Override
    public boolean check(Player player, Location location) {
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
        return claim == null || claim.hasExplicitPermission(player.getUniqueId(), ClaimPermission.Build);
    }

}
