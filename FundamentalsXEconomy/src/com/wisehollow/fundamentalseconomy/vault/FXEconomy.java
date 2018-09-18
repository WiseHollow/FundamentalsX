package com.wisehollow.fundamentalseconomy.vault;

import com.wisehollow.fundamentalseconomy.Main;
import com.wisehollow.fundamentalseconomy.Settings;
import com.wisehollow.fundamentalseconomy.data.EconomyProfile;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Optional;

import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.entity.Player;

public class FXEconomy implements Economy {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return Main.getPlugin().getDescription().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double money) {
        return money + " " + (money == 1 ? currencyNameSingular() : currencyNamePlural());
    }

    @Override
    public String currencyNamePlural() {
        return Settings.currencyPlural;
    }

    @Override
    public String currencyNameSingular() {
        return Settings.currencySingular;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAccount(String worldName) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return EconomyProfile.exists(offlinePlayer);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String worldName) {
        return hasAccount(offlinePlayer);
    }

    @SuppressWarnings("deprecation")
    @Override
    public double getBalance(String playerName) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        Optional<EconomyProfile> profile = EconomyProfile.get(offlinePlayer);
        return profile.map(EconomyProfile::getBalance).orElse(0d);
    }

    @SuppressWarnings("deprecation")
    @Override
    public double getBalance(String playerName, String worldName) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String worldName) {
        return getBalance(offlinePlayer);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean has(String playerName, double balance) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double balance) {
        Optional<EconomyProfile> profile = EconomyProfile.get(offlinePlayer);
        return profile.filter(economyProfile -> economyProfile.getBalance() >= balance).isPresent();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean has(String playerName, String worldName, double balance) {
        return has(playerName, balance);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String worldName, double balance) {
        return has(offlinePlayer, balance);
    }

    @SuppressWarnings("deprecation")
    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (amount <= 0) {
            return new EconomyResponse(amount, 0, ResponseType.FAILURE, "Invalid amount provided.");
        } else {
            Optional<EconomyProfile> optionalProfile = EconomyProfile.get(offlinePlayer);
            if (optionalProfile.isPresent()) {
                EconomyProfile profile = optionalProfile.get();
                if (offlinePlayer.isOnline()) {
                    Player player = offlinePlayer.getPlayer();
                    if (profile.getBalance() - amount >= (Main.getPermissions()
                            .playerHas(player, "Fundamentals.Economy.Loan") ? Settings.minimumBalance : 0)) {
                        profile.subtract(amount).save();
                        return new EconomyResponse(amount, profile.getBalance(), ResponseType.SUCCESS, null);
                    } else {
                        return new EconomyResponse(0d, 0d, ResponseType.FAILURE, "Player balance is not enough.");
                    }
                } else {
                    if (profile.getBalance() - amount >= (Main.getPermissions()
                            .playerHas(null, offlinePlayer, "Fundamentals.Economy.Loan") ? Settings.minimumBalance : 0)) {
                        profile.subtract(amount).save();
                        return new EconomyResponse(amount, profile.getBalance(), ResponseType.SUCCESS, null);
                    } else {
                        return new EconomyResponse(0d, 0d, ResponseType.FAILURE, "Player balance is not enough.");
                    }
                }

            } else {
                return new EconomyResponse(0d, 0d, ResponseType.FAILURE, "Could not locate player profile.");
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return withdrawPlayer(offlinePlayer, amount);
    }

    @SuppressWarnings("deprecation")
    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double amount) {
        if (amount <= 0) {
            return new EconomyResponse(amount, 0, ResponseType.FAILURE, "Invalid amount provided.");
        } else {
            Optional<EconomyProfile> optionalProfile = EconomyProfile.get(offlinePlayer);
            if (optionalProfile.isPresent()) {
                EconomyProfile profile = optionalProfile.get();
                if (profile.getBalance() + amount <= Settings.maximumBalance) {
                    profile.add(amount).save();
                    return new EconomyResponse(amount, profile.getBalance(), ResponseType.SUCCESS, null);
                } else {
                    return new EconomyResponse(0d, 0d, ResponseType.FAILURE, "Player balance has reached maximum.");
                }
            } else {
                return new EconomyResponse(0d, 0d, ResponseType.FAILURE, "Could not locate player profile.");
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String worldName, double amount) {
        return depositPlayer(offlinePlayer, amount);
    }

    @SuppressWarnings("deprecation")
    @Override
    public EconomyResponse createBank(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String s) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String s, double v) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String s, double v) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public EconomyResponse isBankOwner(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public EconomyResponse isBankMember(String s, String s1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean createPlayerAccount(String s, String s1) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }
}
