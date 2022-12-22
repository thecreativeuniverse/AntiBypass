package dev.creativeuniverse.antibypass;

import dev.creativeuniverse.antibypass.config.Config;
import dev.creativeuniverse.antibypass.hooks.BuildHooksManager;
import dev.creativeuniverse.antibypass.papi.blockcheck.BlockCheckNoHookEvent;
import dev.creativeuniverse.antibypass.papi.blockcheck.BlockCheckWithHooks;
import dev.creativeuniverse.antibypass.papi.targetblock.GetTargetBlock;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class AntiBypass extends JavaPlugin {

    private BuildHooksManager buildHooks;

    //TODO: Pretty up the startup logs
    @Override
    public void onEnable() {

        saveDefaultConfig();
        ConfigurationSection config = getConfig();

        registerHooks();

        // Block Checks
        if (config.contains(Config.BLOCK_CHECK)) {
            if (config.getBoolean(Config.BlockCheck.BlockEvent.ENABLED)) {
                getLogger().info("Registering BlockCheck PAPI Expansion via Block Events (no hooks)");
                new BlockCheckNoHookEvent(this).register();
            } else if (config.getBoolean(Config.BlockCheck.Hooks.ENABLED)) {
                getLogger().info("Registering BlockCheck PAPI Expansion via registered Hooks.");
                new BlockCheckWithHooks(this).register();
            }
        }

        // Target Blocks
        if (config.getBoolean(Config.GetTargetBlock.ENABLED, true)) {
            new GetTargetBlock(this).register();
        }

    }

    private void registerHooks() {
        getLogger().info("Registering build-related hooks...");
        this.buildHooks = new BuildHooksManager(this);
        getLogger().info("Registered build-related hooks.");
    }

    public BuildHooksManager getBuildHookManager() {
        return this.buildHooks;
    }

}
