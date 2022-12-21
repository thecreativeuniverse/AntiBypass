package dev.creativeuniverse.antibypass;

import dev.creativeuniverse.antibypass.blockcheck.BlockCheckHooks;
import dev.creativeuniverse.antibypass.blockcheck.BlockCheckNoHookEvent;
import dev.creativeuniverse.antibypass.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiBypass extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigurationSection config = getConfig();

        // Block Checks
        if (config.contains(Config.BLOCK_CHECK)) {
            if (config.getBoolean(Config.BlockCheck.BlockEvent.ENABLED)) {
                new BlockCheckNoHookEvent(this).register();
            } else if (config.getBoolean(Config.BlockCheck.Hooks.ENABLED)) {
                new BlockCheckHooks(this).register();
            }
        }
        
    }

}
