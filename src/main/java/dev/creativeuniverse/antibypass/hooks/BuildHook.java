package dev.creativeuniverse.antibypass.hooks;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface BuildHook {

    boolean check(Player player, Location location);

}
