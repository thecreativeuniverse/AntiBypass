package dev.creativeuniverse.antibypass.papi;

import java.util.Map;

public class Params {

    private final Map<String, String> params;

    /**
     * @param params - Map of key and values. Keys must all be lower case.
     */
    public Params(Map<String, String> params) {
        this.params = params;
    }

    public String get(String key) {
        return getOrDefault(key, null);
    }

    public String getOrDefault(String key, String def) {
        return params.getOrDefault(key.toLowerCase(), def);
    }

    public boolean contains(String key) {
        return params.containsKey(key.toLowerCase());
    }

    @Override
    public String toString() {
        return params.toString();
    }
}
