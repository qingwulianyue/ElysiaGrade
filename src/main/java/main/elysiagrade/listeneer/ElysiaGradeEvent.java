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
            PlayerData playerData = new PlayerData(ElysiaGrade.getConfigManager().getConfigData().getDefaultLevel(), 0,0, LocalDate.now());
            playerManager.setPlayerData(uuid, playerData);
            return;
        }
        checkDailyExperience(uuid);
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player) return;
        if (livingEntity.getKiller() == null) return;
        if (ElysiaGrade.getConfigManager().getConfigData().isDebug())
            ElysiaGrade.getPlugin(ElysiaGrade.class).getLogger().info("§a实体 §f" + livingEntity.getName() + "§a 死亡，击杀者为 §e" + livingEntity.getKiller().getName());
        String displayName = ChatColor.stripColor(livingEntity.getName());
        if (!ElysiaGrade.getConfigManager().getConfigData().getMonsters().containsKey(displayName)) return;
        int experience = ElysiaGrade.getConfigManager().getConfigData().getMonsters().get(displayName);
        ProjectUtils.giveExperience(livingEntity.getKiller().getUniqueId(), experience, false);
    }
    private void checkDailyExperience(UUID uuid) {
        PlayerManager playerManager = ElysiaGrade.getPlayerManager();
        PlayerData playerData = playerManager.getPlayerData(uuid);
        if (playerData.getUpdate_time().equals(LocalDate.now())) return;
        playerManager.setPlayerData(uuid, new PlayerData(playerData.getLevel(), playerData.getExperience(), 0, LocalDate.now()));
    }
}
