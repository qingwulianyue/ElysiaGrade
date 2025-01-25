package main.elysiagrade.command.subcommands;

import org.bukkit.command.CommandSender;

/**
 * 显示命令提示命令
 **/
public class HelpCommand {
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sender.sendMessage("/ElysiaGrade help   -   获取帮助");
            sender.sendMessage("/ElysiaGrade reload   -   重载插件");
            sender.sendMessage("/ElysiaGrade give {player} {exp}   -   给予玩家经验");
            sender.sendMessage("/ElysiaGrade reset {player}   -   重置玩家等级");
            sender.sendMessage("/ElysiaGrade set {player} {level}   -   重置玩家等级");
        }
    }
}
