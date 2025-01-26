package main.elysiagrade.command.subcommands;

import main.elysiagrade.ElysiaGrade;
import main.elysiagrade.filemanager.PlayerManager;
import main.elysiagrade.filemanager.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.UUID;

public class ResetCommand {
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            String playerName = args[1];
            if(Bukkit.getPlayer(playerName) != null){
                reset(playerName);
            }
        }
    }
    private void reset(String playerName){
        Player player = Bukkit.getPlayer(playerName);
        UUID uuid = player.getUniqueId();
        PlayerManager playerManager = ElysiaGrade.getPlayerManager();
        PlayerData playerData = new PlayerData(ElysiaGrade.getConfigManager().getConfigData().getDefaultLevel(), 0, 0, LocalDate.now());
        playerManager.setPlayerData(uuid, playerData);
        player.sendMessage(ElysiaGrade.getConfigManager().getConfigData().getPrefix() + ElysiaGrade.getConfigManager().getConfigData().getMessages().get("level_reset"));
    }
}
