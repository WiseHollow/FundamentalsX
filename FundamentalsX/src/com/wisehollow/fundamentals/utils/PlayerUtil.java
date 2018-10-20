package com.wisehollow.fundamentals.utils;

import com.wisehollow.fundamentals.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

/**
 * Created by John on 10/13/2016.
 */
public class PlayerUtil {
    public static Player GetPlayer(String s) {
        for (Player p : Bukkit.getOnlinePlayers())
            if (p.getName().toLowerCase().contains(s.toLowerCase()))
                return p;
        return null;
    }

    public static String GetPlayerPrefix(Player player) {
        if (Main.getPlugin().getChat() == null)
            return "";

        String prefix = "";
        if (Main.getPlugin().getChat() != null) {
            prefix = ChatColor.translateAlternateColorCodes('&', Main.getPlugin().getChat().getPlayerPrefix(player));
        }

        return prefix;
    }

    public static Optional<String> getOfflinePlayerUUID(String name) {
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine = in.readLine();
                in.close();
                con.disconnect();

                JSONParser parser = new JSONParser();
                JSONObject jsonResponse;
                try {
                    jsonResponse = (JSONObject) parser.parse(inputLine);
                    String uuid = jsonResponse.get("id").toString();
                    uuid = formatUUID(uuid);
                    return Optional.of(uuid);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            con.disconnect();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }

    private static String formatUUID(String uuid) {
        return uuid.substring(0, 8) + "-"
                + uuid.substring(8, 12) + "-"
                + uuid.substring(12, 16) + "-"
                + uuid.substring(16, 20) + "-"
                + uuid.substring(20, 32);
    }

}
