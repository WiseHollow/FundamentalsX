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

    private YamlConfiguration internalEnglishConfig;

    public String prefixInfo;
    public String prefixWarning;

    public String afkKick;
    public String broadcast;
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
    public String shutdownTaskAlreadyStarted, shutdownTaskStarted, shutdownTaskCancelled;
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
    public String nicknameTooLong, nicknameChanged, nicknameRemoved, nicknameChangedOther;
    public String welcome;

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

        internalEnglishConfig = Main.getPlugin().getConfigFromJar("language-en.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public void loadFromFile() {
        YamlConfiguration config = getLanguageConfiguration();

        prefixInfo = ChatColor.translateAlternateColorCodes('&', config.getString("Prefix-Info"));
        prefixWarning = ChatColor.translateAlternateColorCodes('&', config.getString("Prefix-Warning"));

        broadcast = parse(config, "Broadcast");
        unauthorized = parse(config, "Unauthorized");
        notLoggedIn = parse(config, "Not logged in");
        targetNotOnline = parse(config, "Target not online");
        mustBeANumber = parse(config, "Must be a number");
        cannotSendMoneyToSelf = parse(config, "Cannot send money to self");
        moneyReceived = parse(config, "Money received");
        moneySent = parse(config, "Money sent");
        moneySet = parse(config, "Money set");
        teleportToggled = parse(config, "Teleport toggled");
        cannotTeleportToPlayer = parse(config, "Cannot teleport to player");
        configurationReloaded = parse(config, "Configuration reloaded");
        pluginVersion = parse(config, "Plugin version");
        hatSetError = parse(config, "Hat set error");
        hatSet = parse(config, "Hat set");
        pvpTimerEnabled = parse(config, "PVP timer enabled");
        pvpTimerDisabled = parse(config, "PVP timer disabled");
        pvpTimerNone = parse(config, "PVP timer none");
        pvpTimerHowTo = parse(config, "PVP timer how-to");
        cannotPVPWithPlayer = parse(config, "Cannot PVP with player");
        noPreviousLocation = parse(config, "No previous location");
        banPlayer = parse(config, "Ban player");
        burnAll = parse(config, "Burn all");
        burnPlayer = parse(config, "Burn player");
        killedEntities = parse(config, "Killed entities");
        timeSet = parse(config, "Time set");
        homeDoesNotExist = parse(config, "Home does not exist");
        homeRemoved = parse(config, "Home removed");
        jailDoesNotExist = parse(config, "Jail does not exist");
        jailRemoved = parse(config, "Jail removed");
        warpDoesNotExist = parse(config, "Warp does not exist");
        warpRemoved = parse(config, "Warp removed");
        itemNotInHand = parse(config, "Item not in hand");
        invalidEnchantment = parse(config, "Invalid enchantment");
        maxLevelEnchantment = parse(config, "Max level enchantment");
        enchantmentAdded = parse(config, "Enchantment added");
        viewingEnderchest = parse(config, "Viewing enderchest");
        viewingInventory = parse(config, "Viewing inventory");
        fedPlayer = parse(config, "Fed player");
        fed = parse(config, "Fed");
        flightEnabled = parse(config, "Flight enabled");
        flightDisabled = parse(config, "Flight disabled");
        flightEnabledFor = parse(config, "Flight enabled for");
        flightDisabledFor = parse(config, "Flight disabled for");
        gameModeSet = parse(config, "GameMode set");
        gameModeOfPlayerSet = parse(config, "GameMode of player set");
        gameModeInvalid = parse(config, "GameMode invalid");
        materialInvalid = parse(config, "Material invalid");
        receivedItemStack = parse(config, "Received ItemStack");
        godModeEnabled = parse(config, "GodMode enabled");
        godModeDisabled = parse(config, "GodMode disabled");
        godModeEnabledFor = parse(config, "GodMode enabled for");
        godModeDisabledFor = parse(config, "GodMode disabled for");
        healed = parse(config, "Healed");
        healedPlayer = parse(config, "Healed player");
        noHomes = parse(config, "No homes");
        homeList = parse(config, "Home list");
        jailSend = parse(config, "Jail send");
        jailCannotSend = parse(config, "Jail cannot send");
        invalidTimeFormatting = parse(config, "Invalid time formatting");
        kickAll = parse(config, "Kick all");
        kickPlayer = parse(config, "Kick player");
        killPlayer = parse(config, "Kill player");
        kitDoesNotExist = parse(config, "Kit does not exist");
        kitDelayMinutes = parse(config, "Kit delay minutes");
        kitDelaySeconds = parse(config, "Kit delay seconds");
        jailList = parse(config, "Jail list");
        playerMustBeInGameMode = parse(config, "Player must be in GameMode");
        playerCannotBeInGodMode = parse(config, "Player cannot be in GodMode");
        playerCannotBeVanished = parse(config, "Player cannot be vanished");
        nukeTaskStart = parse(config, "Nuke task start");
        nukeTaskStop = parse(config, "Nuke task stop");
        nobodyReply = parse(config, "Nobody reply");
        worldSeed = parse(config, "World seed");
        homesMaxed = parse(config, "Homes maxed");
        homeSet = parse(config, "Home set");
        jailUpdated = parse(config, "Jail updated");
        jailCreated = parse(config, "Jail created");
        maxHealthSet = parse(config, "Max health set");
        maxHealthOfPlayerSet = parse(config, "Max health of player set");
        spawnSet = parse(config, "Spawn set");
        firstJoinSet = parse(config, "First join set");
        warpSet = parse(config, "Warp set");
        smite = parse(config, "Smite");
        invalidEntityType = parse(config, "Invalid entity type");
        spawnEntities = parse(config, "Spawn entities");
        invalidSpeed = parse(config, "Invalid speed");
        flySpeedSet = parse(config, "Fly speed set");
        walkSpeedSet = parse(config, "Walk speed set");
        shutdownTaskAlreadyStarted = parse(config, "Shutdown task already started");
        shutdownTaskStarted = parse(config, "Shutdown task started");
        shutdownTaskCancelled = parse(config, "Shutdown task cancelled");
        sudoPlayer = parse(config, "Sudo player");
        weatherSetSun = parse(config, "Weather set sun");
        weatherSetRain = parse(config, "Weather set rain");
        weatherSetStorm = parse(config, "Weather set storm");
        invalidCoordinates = parse(config, "Invalid coordinates");
        timeCurrent = parse(config, "Time current");
        teleportRequestSent = parse(config, "Teleport request sent");
        teleportRequestReceived = parse(config, "Teleport request received");
        teleportRequestNone = parse(config, "Teleport request none");
        teleportRequestAccept = parse(config, "Teleport request accept");
        teleportRequestDeny = parse(config, "Teleport request deny");
        notBanned = parse(config, "Not banned");
        unbanned = parse(config, "Unbanned");
        notJailed = parse(config, "Not jailed");
        vanished = parse(config, "Vanished");
        noLongerVanished = parse(config, "No longer vanished");
        warpList = parse(config, "Warp list");
        weatherCurrent = parse(config, "Weather current");
        onlinePlayers = parse(config, "Online players");
        isNowAFK = parse(config, "Is now AFK");
        isNoLongerAFK = parse(config, "Is no longer AFK");
        afkKick = parse(config, "AFK Kick");
        youAreJailed = parse(config, "You are jailed");
        youAreJailedForSeconds = parse(config, "You are jailed for seconds");
        youAreJailedForMinutes = parse(config, "You are jailed for minutes");
        youAreReleasedFromJail = parse(config, "You are released from jail");
        teleporting = parse(config, "Teleporting");
        teleportingWarmUp = parse(config, "Teleporting warm up");
        teleportingCancelled = parse(config, "Teleporting cancelled");
        inventoryFullFromKit = parse(config, "Inventory full from kit");
        receivingKit = parse(config, "Receiving kit");
        socialSpyEnabled = parse(config, "Social spy enabled");
        socialSpyDisabled = parse(config, "Social spy disabled");
        balanceSelf = parse(config, "Balance of self");
        balancePlayer = parse(config, "Balance of player");
        mutePlayer = parse(config, "Mute player");
        muted = parse(config, "Muted");
        unmuted = parse(config, "Unmuted");
        mentioned = parse(config, "Mentioned");
        nicknameTooLong = parse(config, "Nickname too long");
        nicknameChanged = parse(config, "Nickname changed");
        nicknameRemoved = parse(config, "Nickname removed");
        nicknameChangedOther = parse(config, "Nickname changed other");
        welcome = parse(config, "Welcome");

        // Who-is
        playerInfo = parse(config, "Player info");
        health = parse(config, "Health");
        food = parse(config, "Food");
        walkSpeed = parse(config, "Walk speed");
        flySpeed = parse(config, "Fly speed");
        gameMode = parse(config, "GameMode");
        location = parse(config, "Location");
        godMode = parse(config, "God mode");
        afk = parse(config, "AFK");
        jail = parse(config, "Jail");
        vanish = parse(config, "Vanish");
        lastLoggedIn = parse(config, "Last logged in");

        // Server stats
        serverStatistics = parse(config, "Server statistics");
        currentTPS = parse(config, "Current TPS");
        maximumMemory = parse(config, "Maximum memory");
        allocatedMemory = parse(config, "Allocated memory");
        freeMemory = parse(config, "Free memory");
        livingEntityList = parse(config, "Living entity list");
        entityList = parse(config, "Entity list");
        chunkList = parse(config, "Chunk list");

        // IO
        loadedFile = parse(config, "File copied");

        Main.getPlugin().getLogger().info("Successfully loaded language.yml!");
    }

    private String parse(YamlConfiguration config, String key) {
        String message = config.getString(key);
        if (message == null && (message = internalEnglishConfig.getString(key)) == null) {
            throw new RuntimeException("Cannot translate null message.");
        }

        message = ChatColor.translateAlternateColorCodes('&', message);
        message = message.replace("%i", prefixInfo);
        message = message.replace("%w", prefixWarning);
        return message;
    }

}
