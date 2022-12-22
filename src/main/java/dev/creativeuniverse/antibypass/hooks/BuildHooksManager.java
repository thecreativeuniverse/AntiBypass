package dev.creativeuniverse.antibypass.hooks;

import dev.creativeuniverse.antibypass.AntiBypass;
import dev.creativeuniverse.antibypass.hooks.impl.GriefPreventionHook;
import dev.creativeuniverse.antibypass.hooks.impl.WorldGuardHook;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class BuildHooksManager {

    private final AntiBypass plugin;
    private final Map<String, BuildHook> activeHooks = new HashMap<>();
    private final Set<String> inactiveHooks = new HashSet<>();

    public BuildHooksManager(AntiBypass plugin) {
        this.plugin = plugin;
        for (HookType type : HookType.values()) {
            plugin.getLogger().info(String.format("Checking for %s...", type.pluginName()));
            if (initialise(type.className())) {
                tryRegister(type);
            } else {
                plugin.getLogger().info(String.format("Could not find %s.", type.pluginName()));
                inactiveHooks.add(type.pluginName());
            }
        }
    }

    public BuildHook getHook(String hookName) {
        return activeHooks.get(hookName.toLowerCase());
    }

    public Set<BuildHook> getHooks(Collection<String> hookNames) {
        Set<BuildHook> hooks = new HashSet<>();
        for (String n : hookNames) {
            n = n.toLowerCase();
            if (activeHooks.containsKey(n)) {
                hooks.add(activeHooks.get(n));
            } else if (inactiveHooks.contains(n)) {
                plugin.getLogger().warning(String.format("Plugin %s is registered in config, not enabled on the server.", n));
            } else {
                plugin.getLogger().warning(String.format("Plugin %s is not currently supported.", n));
            }
        }
        return hooks;
    }

    private void tryRegister(HookType hookType) {
        try {
            BuildHook hook = hookType.getHookClass().getDeclaredConstructor().newInstance();
            plugin.getLogger().info(String.format("Registered %s hook.", hookType.pluginName()));
            activeHooks.put(hookType.pluginName().toLowerCase(), hook);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            inactiveHooks.add(hookType.pluginName().toLowerCase());
        }
    }

    private boolean initialise(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    private enum HookType {
        GRIEFPREVENTION("GriefPrevention", "me.ryanhamshire.GriefPrevention.GriefPrevention", GriefPreventionHook.class),
        WORLD_GUARD("WorldGuard", "com.sk89q.worldguard.bukkit.WorldGuardPlugin", WorldGuardHook.class);

        String pluginName;
        String className;
        Class<? extends BuildHook> hookClass;

        HookType(String pluginName, String className, Class<? extends BuildHook> hookClass) {
            this.pluginName = pluginName;
            this.className = className;
            this.hookClass = hookClass;
        }

        String pluginName() {
            return this.pluginName;
        }

        String className() {
            return this.className;
        }

        public Class<? extends BuildHook> getHookClass() {
            return hookClass;
        }

    }

}
