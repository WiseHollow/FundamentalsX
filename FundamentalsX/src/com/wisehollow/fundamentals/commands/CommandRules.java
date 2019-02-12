package com.wisehollow.fundamentals.commands;

import com.wisehollow.fundamentals.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CommandRules implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!rulesFileExists()) {
            generateRulesFile();
        }

        try {
            tellRules(sender);
        } catch (IOException exception) {
            Main.getPlugin().getLogger().severe("Could not read Rules file.");
            exception.printStackTrace();
        }

        return true;
    }

    private boolean rulesFileExists() {
        return new File("plugins" + File.separator + Main.getPlugin().getName() + File.separator + "rules.txt").exists();
    }

    private void generateRulesFile() {
        final File rulesFile = new File("plugins" + File.separator + Main.getPlugin().getName() + File.separator + "rules.txt");
        if (!rulesFile.exists()) {
            Main.getPlugin().getLogger().info("Rules file not found. Generating new one..");
            Main.getPlugin().exportInternalFile("rules.txt", rulesFile);
        }
    }

    private void tellRules(final CommandSender sender) throws IOException {
        final File rulesFile = new File("plugins" + File.separator + Main.getPlugin().getName() + File.separator + "rules.txt");
        final FileReader fileReader = new FileReader(rulesFile);
        final BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sender.sendMessage(line);
        }
        bufferedReader.close();
    }

}
