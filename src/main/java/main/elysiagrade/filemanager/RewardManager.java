package main.elysiagrade.filemanager;

import main.elysiagrade.ElysiaGrade;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class RewardManager {
    private RewardManager(){}
    private static RewardManager instance = new RewardManager();
    public static RewardManager getInstance(){
        return instance;
    }
    private HashMap<Integer, List<String>> reward = new HashMap<>();
    public void loadReward(){
        reward.clear();
        File file = new File(ElysiaGrade.getPlugin(ElysiaGrade.class).getDataFolder() + "/reward.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            reward.put(Integer.parseInt(key), config.getStringList(key));
        }
        logRewardInfoIfDebug();
    }
    private void logRewardInfoIfDebug(){
        ElysiaGrade plugin = ElysiaGrade.getPlugin(ElysiaGrade.class);
        if (ElysiaGrade.getConfigManager().getConfigData().isDebug()){
            for (Integer key : reward.keySet()){
                plugin.getLogger().info("§e等级 " + key + " 的奖励为：");
                for (String s : reward.get(key)){
                    plugin.getLogger().info("§a" + s);
                }
            }
        }
    }
    public List<String> getReward(int level){
        if (reward.containsKey(level))
            return reward.get(level);
        return null;
    }
}
