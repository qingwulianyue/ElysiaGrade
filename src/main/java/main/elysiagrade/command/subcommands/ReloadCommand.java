package main.elysiagrade.command.subcommands;

import main.elysiagrade.ElysiaGrade;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ElysiaGrade.getConfigManager().loadConfig();
            ElysiaGrade.getRewardManager().loadReward();
            sender.sendMessage("重载完成");
        }
    }
}
