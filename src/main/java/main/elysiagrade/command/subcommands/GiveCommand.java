package main.elysiagrade.command.subcommands;

import main.elysiagrade.ProjectUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GiveCommand {
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            String playerName = args[1];
            if(Bukkit.getPlayer(playerName) != null){
                giveExperience(playerName, Integer.parseInt(args[2]));
            }
        }
    }
    private void giveExperience(String playerName, int experience) {
        Player player = Bukkit.getPlayer(playerName);
        ProjectUtils.giveExperience(player.getUniqueId(), experience);
    }
}
