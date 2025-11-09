package main.elysiagrade.listeneer;

import main.elysiagrade.ElysiaGrade;
import main.elysiagrade.ProjectUtils;
import main.elysiagrade.filemanager.PlayerManager;
import main.elysiagrade.filemanager.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDate;
import java.util.UUID;

/**
 * 事件监听
 **/
public class ElysiaGradeEvent implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        PlayerManager playerManager = ElysiaGrade.getPlayerManager();
        if (playerManager.getPlayerData(uuid) == null){
            if (ElysiaGrade.getConfigManager().getConfigData().isDebug()){
                ElysiaGrade.getPlugin(ElysiaGrade.class).getLogger().info("§e用户 " + player.getName() + " 初次进入服务器，初始化数据");
            }
            PlayerData playerData = new PlayerData(ElysiaGrade.getConfigManager().getConfigData().getDefaultLevel(), 0);
            playerManager.setPlayerData(uuid, playerData);
        }
    }
}
