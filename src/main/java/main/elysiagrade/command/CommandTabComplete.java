package main.elysiagrade.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> subCommands = new ArrayList<>();
        if (strings.length == 1) {
            subCommands.add("help");
            subCommands.add("reload");
            subCommands.add("give");
            subCommands.add("reset");
        }
        else if (strings.length == 2){
            if (strings[0].equalsIgnoreCase("give")) {
                for (Player player : Bukkit.getOnlinePlayers())
                    subCommands.add(player.getName());
            }
            else if (strings[0].equalsIgnoreCase("reset")) {
                for (Player player : Bukkit.getOnlinePlayers())
                    subCommands.add(player.getName());
            }
            else if (strings[0].equalsIgnoreCase("set")) {
                for (Player player : Bukkit.getOnlinePlayers())
                    subCommands.add(player.getName());
            }
        }
        return subCommands;
    }
}
