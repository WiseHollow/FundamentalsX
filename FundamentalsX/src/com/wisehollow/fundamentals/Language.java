package com.wisehollow.fundamentals;

import org.bukkit.ChatColor;

/**
 * Created by John on 10/13/2016.
 */
public class Language {
    public static final String PREFIX = ChatColor.GOLD + "";
    public static final String PREFIX_WARNING = ChatColor.DARK_RED + "[Warn] ";
    public static final String DoesNotHavePermission = PREFIX_WARNING + "You do not have permission for this function.";
    public static final String YouMustBeLoggedIn = PREFIX_WARNING + "You must be logged in to do this.";
    public static final String PlayerMustBeLoggedIn = PREFIX_WARNING + "That player must be logged in for this.";
    public static final String MustBeANumber = PREFIX_WARNING + "What value you entered is not numerical.";
    public static final String CannotSendYourselfMoney = PREFIX_WARNING + "You cannot send yourself money.";
    public static final String SentMoney = PREFIX + "You sent %1 to %2.";
    public static final String ReceivedMoney = PREFIX + "You received %1 from %2.";
    public static final String MoneySetTo = PREFIX + "Your cash balance was set to %1";
    public static final String HasTeleportDisabled = PREFIX + "That player has their teleport disabled.";
    public static final String TeleportHasBeenToggled = PREFIX + "Teleporting to you has been toggled ";
    public static final String ConfigurationsReloaded = PREFIX + "Configuration files have been reloaded.";
    public static final String CannotSetAsHat = PREFIX + "You cannot set that as a hat.";
    public static final String SetHat = PREFIX + "You've equipped a new hat!";
}
