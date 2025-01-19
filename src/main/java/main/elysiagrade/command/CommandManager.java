package main.elysiagrade.command;

import main.elysiagrade.command.subcommands.GiveCommand;
import main.elysiagrade.command.subcommands.HelpCommand;
import main.elysiagrade.command.subcommands.ReloadCommand;
import main.elysiagrade.command.subcommands.ResetCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) {
            new HelpCommand().execute(commandSender, strings);
            return true;
        }
        String subCommand = strings[0].toLowerCase();
        switch (subCommand) {
            case "give":
                new GiveCommand().execute(commandSender, strings);
                return true;
            case "reload":
                new ReloadCommand().execute(commandSender, strings);
                return true;
            case "reset":
                new ResetCommand().execute(commandSender, strings);
                return true;
            default:
                new HelpCommand().execute(commandSender, strings);
                return true;
        }
    }
}
