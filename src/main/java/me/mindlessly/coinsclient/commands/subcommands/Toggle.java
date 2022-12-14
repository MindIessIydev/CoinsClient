package me.mindlessly.coinsclient.commands.subcommands;

import me.mindlessly.coinsclient.Config;
import me.mindlessly.coinsclient.Main;
import me.mindlessly.coinsclient.client.Client;
import me.mindlessly.coinsclient.utils.Utils;
import net.minecraft.command.ICommandSender;

public class Toggle implements Subcommand {
    public Toggle() {
    }

    public static void updateConfig() {
        if (Config.enabled) {
            Utils.sendMessageWithPrefix("&aFlipper enabled.");
        } else {
            Utils.sendMessageWithPrefix("&cFlipper disabled.");
        }
    }

    @Override
    public String getCommandName() {
        return "toggle";
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public String getCommandUsage() {
        return "";
    }

    @Override
    public String getCommandDescription() {
        return "Toggles the flipper on or off";
    }

    @Override
    public boolean processCommand(ICommandSender sender, String[] args) {
        Config.enabled = !Config.enabled;
        Client.checkIfUpdate();
        Main.config.writeData();
        updateConfig();
        return true;
    }
}