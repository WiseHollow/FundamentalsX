package com.wisehollow.fundamentals.utils;

import com.wisehollow.fundamentals.Main;
import org.bukkit.configuration.file.YamlConfiguration;

public final class SettingsUtil {

    private static YamlConfiguration internalConfigurationFile;

    public static <T> T getValueFromConfiguration(final String key, final Class<T> klass) {
        Object value = Main.getPlugin().getConfig().get(key);

        if (value == null) {
            if (internalConfigurationFile == null && (internalConfigurationFile = Main.getPlugin().getConfigFromJar("config.yml")) == null) {
                throw new RuntimeException("Could not access internal configuration file!");
            }

            value = internalConfigurationFile.get(key);
        }

        if (value != null) {
            try {
                return klass.cast(value);
            } catch(ClassCastException e) {
                return null;
            }
        } else {
            return null;
        }
    }

}
