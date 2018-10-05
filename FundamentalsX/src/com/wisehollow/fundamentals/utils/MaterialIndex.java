package com.wisehollow.fundamentals.utils;

import org.bukkit.Material;

/**
 * Created by John on 10/20/2016.
 */
public class MaterialIndex {
    public static Material getMaterial(String input) {
        input = input.toUpperCase();
        Material mat;
        mat = Material.getMaterial(input);
        if (mat != null)
            return mat;

        for (Material m : Material.values())
            if (m.name().replaceAll("_", "").equalsIgnoreCase(input))
                return m;

        return null;
    }


}
