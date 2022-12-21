package dev.creativeuniverse.antibypass.config;

public class Config {

    public static final String BLOCK_CHECK = "blockcheck";

    public static class BlockCheck {
        public static final String BLOCK_EVENT = join(BLOCK_CHECK, "blockEvent");

        public static class BlockEvent {
            public static final String TYPE = join(BLOCK_EVENT, "type");

            public static class Type {
                public static final String BREAK = "blockBreak";
                public static final String PLACE = "blockPlace";
            }

            public static final String ENABLED = join(BLOCK_EVENT, "enabled");

            public static final String BLACKLIST = join(BLOCK_EVENT, "blacklist");

            public static class BlackList {
                public static final String PLUGINS = join(BLACKLIST, "plugin");
                public static final String CLASS = join(BLACKLIST, "class");
            }

            public static final String WHITELIST = join(BLOCK_EVENT, "whitelist");

            public static class Whitelist {
                public static final String PLUGINS = join(WHITELIST, "plugin");
                public static final String CLASS = join(WHITELIST, "class");
            }

        }

        public static final String HOOKS = join(BLOCK_CHECK, "hooks");

        public static class Hooks {
            public static final String ENABLED = join(HOOKS, "enabled");
            public static final String INCLUDE = join(HOOKS, "include");
        }
    }

    private static String join(String... strings) {
        return String.join(".", strings);
    }

}
