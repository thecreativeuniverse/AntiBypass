package dev.creativeuniverse.antibypass.papi;

import dev.creativeuniverse.antibypass.AntiBypass;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public abstract class AntiBypassPAPI extends PlaceholderExpansion {

    protected final AntiBypass plugin;

    public AntiBypassPAPI(AntiBypass plugin) {
        this.plugin = plugin;
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

    protected Params getParams(String rawParams) {
        if (rawParams.isEmpty()) return new Params(new HashMap<>());
        String[] params = rawParams.split(";");
        Map<String, String> processed = new HashMap<>();
        for (String param : params) {
            String[] separated = param.split("=", 2);
            if (separated.length != 2) {
                plugin.getLogger().warning(String.format("Could not parse %s within %s. If you believe this is an error, please contact the developer.", param, rawParams));
                continue;
            }
            processed.put(separated[0].toLowerCase(), separated[1]);
        }
        return new Params(processed);
    }

}
