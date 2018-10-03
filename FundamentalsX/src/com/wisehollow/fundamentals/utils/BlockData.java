package com.wisehollow.fundamentals.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Created by John on 10/16/2016.
 */
public class BlockData {
    private byte data;
    private Material material;

    public BlockData(Block block) {
        material = block.getType();
        data = block.getData();
    }

    public byte getData() {
        return data;
    }

    public Material getMaterial() {
        return material;
    }
}
