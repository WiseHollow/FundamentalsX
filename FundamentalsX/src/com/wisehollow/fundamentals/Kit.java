package com.wisehollow.fundamentals;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by John on 10/20/2016.
 */
public class Kit {
    private static List<Kit> kitList = new ArrayList<>();

    public static List<Kit> GetAvailableKits(Player p) {
        List<Kit> list = kitList.stream().filter(k -> p.hasPermission(k.getPermissionNode())).collect(Collectors.toList());

        return list;
    }

    public static boolean AddKit(Kit kit) {
        if (KitExists(kit.name))
            return false;
        return kitList.add(kit);
    }

    public static boolean RemoveKit(Kit kit) {
        return kitList.remove(kit);
    }

    public static boolean KitExists(String name) {
        for (Kit k : kitList)
            if (k.name.equalsIgnoreCase(name))
                return true;
        return false;
    }

    public static Kit GetKit(String name) {
        for (Kit k : kitList)
            if (k.name.equalsIgnoreCase(name))
                return k;
        return null;
    }

    private String name;
    private List<ItemStack> items;
    private int delay;

    public Kit(String displayName, int delay) {
        this.name = displayName;
        this.items = new ArrayList<>();
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    public void clearItems() {
        items.clear();
    }

    public void addItem(ItemStack item) {
        items.add(item);
    }

    public void give(Player player) {
        player.sendMessage(Language.PREFIX + "Receiving items from Kit: " + name);
        boolean full = false;

        for (ItemStack item : items) {
            if (player.getInventory().firstEmpty() != -1)
                player.getInventory().addItem(item);
            else {
                player.getWorld().dropItem(player.getLocation(), item);
                full = true;
            }
        }

        if (full)
            player.sendMessage(Language.PREFIX + "Inventory full, item(s) dropped. ");
    }

    public String getPermissionNode() {
        return "Fundamentals.Kits." + name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Kit))
            return false;

        Kit kit = (Kit) o;
        if (name.equalsIgnoreCase(kit.name))
            return true;

        return false;
    }
}
