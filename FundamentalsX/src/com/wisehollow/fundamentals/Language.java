package com.wisehollow.fundamentals;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Created by John on 10/13/2016.
 */
public class Language {

    private static Language language;
    public static Language getInstance() {
        if (language == null) {
            language = new Language();
            language.loadFromFile();
        }

        return language;
    }

    public String prefixInfo;
    public String prefixWarning;

    public String unauthorized;
    public String notLoggedIn;
    public String targetNotOnline;
    public String mustBeANumber;
    public String cannotSendMoneyToSelf, moneyReceived, moneySent, moneySet;
    public String teleportToggled, cannotTeleportToPlayer;
    public String configurationReloaded, pluginVersion;
    public String hatSetError, hatSet;
    public String pvpTimerEnabled, pvpTimerDisabled, pvpTimerNone, pvpTimerHowTo, cannotPVPWithPlayer;
    public String noPreviousLocation;
    public String banPlayer;
    public String burnAll, burnPlayer;
    public String killedEntities;
    public String timeSet;
    public String homeDoesNotExist, homeRemoved, noHomes, homeList, homesMaxed, homeSet;
    public String jailDoesNotExist, jailRemoved, jailSend, jailCannotSend, jailList, jailUpdated, jailCreated;
    public String warpDoesNotExist, warpRemoved, warpSet, warpList;
    public String itemNotInHand;
    public String invalidEnchantment, maxLevelEnchantment, enchantmentAdded;
    public String viewingEnderchest, viewingInventory;
    public String fedPlayer, fed;
    public String flightEnabled, flightDisabled, flightEnabledFor, flightDisabledFor;
    public String gameModeSet, gameModeOfPlayerSet, gameModeInvalid;
    public String materialInvalid;
    public String receivedItemStack;
    public String godModeEnabled, godModeDisabled, godModeEnabledFor, godModeDisabledFor;
    public String healed, healedPlayer;
    public String invalidTimeFormatting;
    public String kickAll, kickPlayer;
    public String killPlayer;
    public String kitDoesNotExist, kitDelayMinutes, kitDelaySeconds;
    public String playerMustBeInGameMode, playerCannotBeInGodMode, playerCannotBeVanished;
    public String nukeTaskStart, nukeTaskStop;
    public String nobodyReply;
    public String worldSeed;
    public String maxHealthSet, maxHealthOfPlayerSet;
    public String spawnSet, firstJoinSet;
    public String smite;
    public String invalidEntityType;
    public String spawnEntities;
    public String invalidSpeed, flySpeedSet, walkSpeedSet;
    public String shutdownTaskAlreadyStarted;
    public String sudoPlayer;
    public String weatherSetSun, weatherSetRain, weatherSetStorm, weatherCurrent;
    public String invalidCoordinates;
    public String timeCurrent;
    public String teleportRequestSent, teleportRequestReceived, teleportRequestNone;
    public String teleportRequestAccept, teleportRequestDeny;
    public String notBanned, unbanned;
    public String notJailed;
    public String vanished, noLongerVanished;
    public String onlinePlayers;
    public String isNowAFK, isNoLongerAFK;
    public String youAreJailed, youAreJailedForSeconds, youAreJailedForMinutes, youAreReleasedFromJail;
    public String teleporting, teleportingWarmUp, teleportingCancelled;
    public String inventoryFullFromKit, receivingKit;
    public String socialSpyEnabled, socialSpyDisabled;
    public String balanceSelf, balancePlayer;
    public String mutePlayer, muted, unmuted;
    public String mentioned;


    public String playerInfo, health, food, walkSpeed, flySpeed, gameMode, location, godMode, afk, jail, vanish, lastLoggedIn;

    public String serverStatistics, currentTPS, maximumMemory, allocatedMemory, freeMemory;
    public String livingEntityList, entityList, chunkList;

    public String loadedFile;

    private Language() { }

    private YamlConfiguration getLanguageConfiguration() {
        File file = new File("plugins" + File.separator + Main.getPlugin().getName() + File.separator + "language.yml");
        if (!file.exists()) {
            throw new RuntimeException("language.yml could not be found in your " + Main.getPlugin().getName() + " directory.");
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    private void loadFromFile() {
        YamlConfiguration config = getLanguageConfiguration();

        prefixInfo = ChatColor.translateAlternateColorCodes('&', config.getString("Prefix-Info"));
        prefixWarning = ChatColor.translateAlternateColorCodes('&', config.getString("Prefix-Warning"));

        unauthorized = parse(config.getString("Unauthorized"));
        notLoggedIn = parse(config.getString("Not logged in"));
        targetNotOnline = parse(config.getString("Target not online"));
        mustBeANumber = parse(config.getString("Must be a number"));
        cannotSendMoneyToSelf = parse(config.getString("Cannot send money to self"));
        moneyReceived = parse(config.getString("Money received"));
        moneySent = parse(config.getString("Money sent"));
        moneySet = parse(config.getString("Money set"));
        teleportToggled = parse(config.getString("Teleport toggled"));
        cannotTeleportToPlayer = parse(config.getString("Cannot teleport to player"));
        configurationReloaded = parse(config.getString("Configuration reloaded"));
        pluginVersion = parse(config.getString("Plugin version"));
        hatSetError = parse(config.getString("Hat set error"));
        hatSet = parse(config.getString("Hat set"));
        pvpTimerEnabled = parse(config.getString("PVP timer enabled"));
        pvpTimerDisabled = parse(config.getString("PVP timer disabled"));
        pvpTimerNone = parse(config.getString("PVP timer none"));
        pvpTimerHowTo = parse(config.getString("PVP timer how-to"));
        cannotPVPWithPlayer = parse(config.getString("Cannot PVP with player"));
        noPreviousLocation = parse(config.getString("No previous location"));
        banPlayer = parse(config.getString("Ban player"));
        burnAll = parse(config.getString("Burn all"));
        burnPlayer = parse(config.getString("Burn player"));
        killedEntities = parse(config.getString("Killed entities"));
        timeSet = parse(config.getString("Time set"));
        homeDoesNotExist = parse(config.getString("Home does not exist"));
        homeRemoved = parse(config.getString("Home removed"));
        jailDoesNotExist = parse(config.getString("Jail does not exist"));
        jailRemoved = parse(config.getString("Jail removed"));
        warpDoesNotExist = parse(config.getString("Warp does not exist"));
        warpRemoved = parse(config.getString("Warp removed"));
        itemNotInHand = parse(config.getString("Item not in hand"));
        invalidEnchantment = parse(config.getString("Invalid enchantment"));
        maxLevelEnchantment = parse(config.getString("Max level enchantment"));
        enchantmentAdded = parse(config.getString("Enchantment added"));
        viewingEnderchest = parse(config.getString("Viewing enderchest"));
        viewingInventory = parse(config.getString("Viewing inventory"));
        fedPlayer = parse(config.getString("Fed player"));
        fed = parse(config.getString("Fed"));
        flightEnabled = parse(config.getString("Flight enabled"));
        flightDisabled = parse(config.getString("Flight disabled"));
        flightEnabledFor = parse(config.getString("Flight enabled for"));
        flightDisabledFor = parse(config.getString("Flight disabled for"));
        gameModeSet = parse(config.getString("GameMode set"));
        gameModeOfPlayerSet = parse(config.getString("GameMode of player set"));
        gameModeInvalid = parse(config.getString("GameMode invalid"));
        materialInvalid = parse(config.getString("Material invalid"));
        receivedItemStack = parse(config.getString("Received ItemStack"));
        godModeEnabled = parse(config.getString("GodMode enabled"));
        godModeDisabled = parse(config.getString("GodMode disabled"));
        godModeEnabledFor = parse(config.getString("GodMode enabled for"));
        godModeDisabledFor = parse(config.getString("GodMode disabled for"));
        healed = parse(config.getString("Healed"));
        healedPlayer = parse(config.getString("Healed player"));
        noHomes = parse(config.getString("No homes"));
        homeList = parse(config.getString("Home list"));
        jailSend = parse(config.getString("Jail send"));
        jailCannotSend = parse(config.getString("Jail cannot send"));
        invalidTimeFormatting = parse(config.getString("Invalid time formatting"));
        kickAll = parse(config.getString("Kick all"));
        kickPlayer = parse(config.getString("Kick player"));
        killPlayer = parse(config.getString("Kill player"));
        kitDoesNotExist = parse(config.getString("Kit does not exist"));
        kitDelayMinutes = parse(config.getString("Kit delay minutes"));
        kitDelaySeconds = parse(config.getString("Kit delay seconds"));
        jailList = parse(config.getString("Jail list"));
        playerMustBeInGameMode = parse(config.getString("Player must be in GameMode"));
        playerCannotBeInGodMode = parse(config.getString("Player cannot be in GodMode"));
        playerCannotBeVanished = parse(config.getString("Player cannot be vanished"));
        nukeTaskStart = parse(config.getString("Nuke task start"));
        nukeTaskStop = parse(config.getString("Nuke task stop"));
        nobodyReply = parse(config.getString("Nobody reply"));
        worldSeed = parse(config.getString("World seed"));
        homesMaxed = parse(config.getString("Homes maxed"));
        homeSet = parse(config.getString("Home set"));
        jailUpdated = parse(config.getString("Jail updated"));
        jailCreated = parse(config.getString("Jail created"));
        maxHealthSet = parse(config.getString("Max health set"));
        maxHealthOfPlayerSet = parse(config.getString("Max health of player set"));
        spawnSet = parse(config.getString("Spawn set"));
        firstJoinSet = parse(config.getString("First join set"));
        warpSet = parse(config.getString("Warp set"));
        smite = parse(config.getString("Smite"));
        invalidEntityType = parse(config.getString("Invalid entity type"));
        spawnEntities = parse(config.getString("Spawn entities"));
        invalidSpeed = parse(config.getString("Invalid speed"));
        flySpeedSet = parse(config.getString("Fly speed set"));
        walkSpeedSet = parse(config.getString("Walk speed set"));
        shutdownTaskAlreadyStarted = parse(config.getString("Shutdown task already started"));
        sudoPlayer = parse(config.getString("Sudo player"));
        weatherSetSun = parse(config.getString("Weather set sun"));
        weatherSetRain = parse(config.getString("Weather set rain"));
        weatherSetStorm = parse(config.getString("Weather set storm"));
        invalidCoordinates = parse(config.getString("Invalid coordinates"));
        timeCurrent = parse(config.getString("Time current"));
        teleportRequestSent = parse(config.getString("Teleport request sent"));
        teleportRequestReceived = parse(config.getString("Teleport request received"));
        teleportRequestNone = parse(config.getString("Teleport request none"));
        teleportRequestAccept = parse(config.getString("Teleport request accept"));
        teleportRequestDeny = parse(config.getString("Teleport request deny"));
        notBanned = parse(config.getString("Not banned"));
        unbanned = parse(config.getString("Unbanned"));
        notJailed = parse(config.getString("Not jailed"));
        vanished = parse(config.getString("Vanished"));
        noLongerVanished = parse(config.getString("No longer vanished"));
        warpList = parse(config.getString("Warp list"));
        weatherCurrent = parse(config.getString("Weather current"));
        onlinePlayers = parse(config.getString("Online players"));
        isNowAFK = parse(config.getString("Is now AFK"));
        isNoLongerAFK = parse(config.getString("Is no longer AFK"));
        youAreJailed = parse(config.getString("You are jailed"));
        youAreJailedForSeconds = parse(config.getString("You are jailed for seconds"));
        youAreJailedForMinutes = parse(config.getString("You are jailed for minutes"));
        youAreReleasedFromJail = parse(config.getString("You are released from jail"));
        teleporting = parse(config.getString("Teleporting"));
        teleportingWarmUp = parse(config.getString("Teleporting warm up"));
        teleportingCancelled = parse(config.getString("Teleporting cancelled"));
        inventoryFullFromKit = parse(config.getString("Inventory full from kit"));
        receivingKit = parse(config.getString("Receiving kit"));
        socialSpyEnabled = parse(config.getString("Social spy enabled"));
        socialSpyDisabled = parse(config.getString("Social spy disabled"));
        balanceSelf = parse(config.getString("Balance of self"));
        balancePlayer = parse(config.getString("Balance of player"));
        mutePlayer = parse(config.getString("Mute player"));
        muted = parse(config.getString("Muted"));
        unmuted = parse(config.getString("Unmuted"));
        mentioned = parse(config.getString("Mentioned"));

        // Who-is
        playerInfo = parse(config.getString("Player info"));
        health = parse(config.getString("Health"));
        food = parse(config.getString("Food"));
        walkSpeed = parse(config.getString("Walk speed"));
        flySpeed = parse(config.getString("Fly speed"));
        gameMode = parse(config.getString("GameMode"));
        location = parse(config.getString("Location"));
        godMode = parse(config.getString("God mode"));
        afk = parse(config.getString("AFK"));
        jail = parse(config.getString("Jail"));
        vanish = parse(config.getString("Vanish"));
        lastLoggedIn = parse(config.getString("Last logged in"));

        // Server stats
        serverStatistics = parse(config.getString("Server statistics"));
        currentTPS = parse(config.getString("Current TPS"));
        maximumMemory = parse(config.getString("Maximum memory"));
        allocatedMemory = parse(config.getString("Allocated memory"));
        freeMemory = parse(config.getString("Free memory"));
        livingEntityList = parse(config.getString("Living entity list"));
        entityList = parse(config.getString("Entity list"));
        chunkList = parse(config.getString("Chunk list"));

        // IO
        loadedFile = parse(config.getString("File copied"));

        Main.getPlugin().getLogger().info("Successfully loaded language.yml!");
    }

    private String parse(String message) {
        if (message == null) {
            throw new RuntimeException("Cannot translate null message.");
        }

        message = ChatColor.translateAlternateColorCodes('&', message);
        message = message.replace("%i", prefixInfo);
        message = message.replace("%w", prefixWarning);
        return message;
    }

}
