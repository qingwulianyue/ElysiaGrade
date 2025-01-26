package main.elysiagrade;

import main.elysiagrade.filemanager.PlayerManager;
import main.elysiagrade.filemanager.data.PlayerData;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
/**
 * 项目公用工具类
 **/

public class ProjectUtils {
    /**
     * 给予玩家经验值
     * @param uuid 玩家UUID
     * @param experience 经验值
     **/
    public static void giveExperience(UUID uuid, int experience, boolean command) {
        Player player = Bukkit.getPlayer(uuid);
        PlayerManager playerManager = ElysiaGrade.getPlayerManager();
        PlayerData playerData = playerManager.getPlayerData(uuid);
        String playerName = player.getName();
        if (playerData == null){
            ElysiaGrade.getPlugin(ElysiaGrade.class).getLogger().info("§c玩家 " + playerName + " 的数据不存在，请使用 /elysia grade reset " + playerName + " 重置数据");
            return;
        }
        if (command){
            player.sendMessage(
                    ElysiaGrade.getConfigManager().getConfigData().getPrefix() +
                            ElysiaGrade.getConfigManager().getConfigData().getMessages().get("get_experience").replaceAll("%experience%", String.valueOf(experience))
            );
            addExperience(playerData, uuid, experience);
        } else if (playerData.getDaily_experience() >= ElysiaGrade.getConfigManager().getConfigData().getDaily_experience()) {
            player.sendMessage(
                    ElysiaGrade.getConfigManager().getConfigData().getPrefix() +
                            ElysiaGrade.getConfigManager().getConfigData().getMessages().get("experience_max")
            );
        } else if (playerData.getDaily_experience() + experience <= ElysiaGrade.getConfigManager().getConfigData().getDaily_experience()){
            player.sendMessage(
                    ElysiaGrade.getConfigManager().getConfigData().getPrefix() +
                            ElysiaGrade.getConfigManager().getConfigData().getMessages().get("get_experience").replaceAll("%experience%", String.valueOf(experience))
            );
            addExperience(playerData, uuid, experience);
        } else if (playerData.getDaily_experience() + experience > ElysiaGrade.getConfigManager().getConfigData().getDaily_experience()) {
            player.sendMessage(
                    ElysiaGrade.getConfigManager().getConfigData().getPrefix() +
                            ElysiaGrade.getConfigManager().getConfigData().getMessages().get("get_experience").replaceAll("%experience%", String.valueOf(ElysiaGrade.getConfigManager().getConfigData().getDaily_experience() - playerData.getDaily_experience()))
            );
            addExperience(playerData, uuid, (int) (ElysiaGrade.getConfigManager().getConfigData().getDaily_experience() - playerData.getDaily_experience()));
        }
    }
    /**
     * 给予玩家奖励
     * @param uuid 玩家UUID
     * @param level 玩家等级
     **/
    private static void giveReward(UUID uuid, int level) {
        List<String> reward = ElysiaGrade.getRewardManager().getReward(level);
        if (reward == null) return;
        for (String s : reward){
            parseCommand(s, uuid);
        }
    }
    /**
     * 解析命令
     * @param command 命令
     * @param uuid 玩家UUID
     **/
    private static void parseCommand(String command, UUID uuid) {
        if (command.startsWith("[console]")) {
            command = command.replace("[console]", "");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", Bukkit.getPlayer(uuid).getName()));
        } else if (command.startsWith("[message]")) {
            command = command.replace("[message]", "");
            Bukkit.getPlayer(uuid).sendMessage(command.replaceAll("%player%", Bukkit.getPlayer(uuid).getName()));
        }
    }
    public static String getExperienceFormula(int level){
        level++;
        HashMap<String, String> experience = ElysiaGrade.getConfigManager().getConfigData().getExperience();
        for (String key : experience.keySet()){
            if(key.contains("-")){
                String[] split = key.split("-");
                if (level >= Integer.parseInt(split[0]) && level <= Integer.parseInt(split[1]))
                    return experience.get(key).replaceAll("level", String.valueOf(level));
            } else if (level >= Integer.parseInt(key))
                return experience.get(key).replaceAll("level", String.valueOf(level));
        }
        return null;
    }
    private static void addExperience(PlayerData playerData, UUID uuid, int experience) {
        Player player = Bukkit.getPlayer(uuid);
        PlayerManager playerManager = ElysiaGrade.getPlayerManager();
        int level = playerData.getLevel();
        double nowExperience = playerData.getExperience();
        double maxExperience = 0;
        double newExperience;
        //计算当前等级最大经验值
        if (ElysiaGrade.getConfigManager().getConfigData().getType().equals("formula")){
            //获取经验计算公式
            String formula = getExperienceFormula(level);
            //计算最大经验值
            if (formula != null) {
                maxExperience = new ExpressionBuilder(formula).build().evaluate();
            }
        }
        //计算等级升级
        if (nowExperience + experience >= maxExperience) {
            //等级提升
            level = level + 1;
            player.sendMessage(
                    ElysiaGrade.getConfigManager().getConfigData().getPrefix() +
                            ElysiaGrade.getConfigManager().getConfigData().getMessages().get("level_up").replaceAll("%level%", String.valueOf(level))
            );
            giveReward(uuid, level);
            PlayerData newPlayerData = new PlayerData(level, 0, Math.min(playerData.getDaily_experience() + maxExperience - nowExperience, ElysiaGrade.getConfigManager().getConfigData().getDaily_experience()), LocalDate.now());
            playerManager.setPlayerData(uuid, newPlayerData);
            addExperience(newPlayerData, uuid, (int) (experience - (maxExperience - nowExperience)));
        } else {
            //等级未升级
            newExperience = nowExperience + experience;
            playerManager.setPlayerData(uuid, new PlayerData(level, newExperience, Math.min(playerData.getDaily_experience() + experience, ElysiaGrade.getConfigManager().getConfigData().getDaily_experience()), LocalDate.now()));
        }
    }
}
