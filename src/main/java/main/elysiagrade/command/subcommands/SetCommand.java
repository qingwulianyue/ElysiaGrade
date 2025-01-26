package main.elysiagrade.command.subcommands;

import main.elysiagrade.ElysiaGrade;
import main.elysiagrade.filemanager.PlayerManager;
import main.elysiagrade.filemanager.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.time.LocalDate;

public class SetCommand {
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            String playerName = args[1];
            PlayerManager playerManager = ElysiaGrade.getPlayerManager();
            PlayerData playerData = playerManager.getPlayerData(Bukkit.getPlayer(playerName).getUniqueId());
            playerManager.setPlayerData(Bukkit.getPlayer(playerName).getUniqueId(), new PlayerData(Integer.parseInt(args[2]), 0, playerData.getDaily_experience(), LocalDate.now()));
        }
    }
}
