package me.mindlessly.coinsclient.commands;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import gg.essential.api.EssentialAPI;
import me.mindlessly.coinsclient.Main;
import me.mindlessly.coinsclient.Reference;
import me.mindlessly.coinsclient.commands.subcommands.Subcommand;
import me.mindlessly.coinsclient.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CCCommand extends CommandBase {
    private final Subcommand[] subcommands;

    public CCCommand(Subcommand[] subcommands) {
        this.subcommands = subcommands;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getCommandAliases() {
        return Arrays.asList("coinsclient", "coinsclien", "nec");
    }

    @Override
    public String getCommandName() {
        return "cc";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/cc <subcommand> <arguments>";
    }

    public void sendHelp(ICommandSender sender) {
        List<String> commandUsages = new LinkedList<>();
        for (Subcommand subcommand : this.subcommands) {
            if (!subcommand.isHidden()) {
                commandUsages.add(EnumChatFormatting.AQUA + "/nec " + subcommand.getCommandName() + " "
                    + subcommand.getCommandUsage() + EnumChatFormatting.DARK_AQUA + " - " + subcommand.getCommandDescription());
            }
        }
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "CC " + EnumChatFormatting.GREEN
            + Reference.VERSION + "\n" + String.join("\n", commandUsages)));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            EssentialAPI.getGuiUtil().openScreen(Main.config.gui());
            return;
        }
        for (Subcommand subcommand : this.subcommands) {
            if (Objects.equals(args[0], subcommand.getCommandName())) {
                if (!subcommand.processCommand(sender, Arrays.copyOfRange(args, 1, args.length))) {
                    // processCommand returned false
                    Utils.sendMessageWithPrefix("&cFailed to execute command, command usage: /nec "
                        + subcommand.getCommandName() + " " + subcommand.getCommandUsage());
                }
                return;
            }
        }
        Utils.sendMessageWithPrefix(EnumChatFormatting.RED
            + "The subcommand wasn't found, please refer to the help message below for the list of subcommands");
        sendHelp(sender);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> possibilities = new LinkedList<>();
        for (Subcommand subcommand : subcommands) {
            possibilities.add(subcommand.getCommandName());
        }
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, possibilities);
        }
        return null;
    }
}