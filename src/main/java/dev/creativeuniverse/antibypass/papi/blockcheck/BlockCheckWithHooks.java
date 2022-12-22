package dev.creativeuniverse.antibypass.papi.blockcheck;

import dev.creativeuniverse.antibypass.AntiBypass;
import dev.creativeuniverse.antibypass.config.Config;
import dev.creativeuniverse.antibypass.hooks.BuildHook;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockCheckWithHooks extends BlockCheck {

    private final Set<BuildHook> hooks = new HashSet<>();

    public BlockCheckWithHooks(AntiBypass plugin) {
        super(plugin);
        List<String> hookNames = (plugin.getConfig().getStringList(Config.BlockCheck.Hooks.INCLUDE));
        hooks.addAll(plugin.getBuildHookManager().getHooks(hookNames));
    }

    // %blockcheck_x=<x>;y=<y>;z=<z>;world=<world_name>%
    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        Location blockLoc = getLocation(params);
        for (BuildHook hook : hooks) {
            if (!hook.check(player, blockLoc)) return String.valueOf(false);
        }
        return String.valueOf(true);
    }

}
