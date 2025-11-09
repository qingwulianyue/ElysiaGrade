package main.elysiagrade.filemanager;

import main.elysiagrade.ElysiaGrade;
import main.elysiagrade.filemanager.data.ConfigData;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
/**
 * 配置文件管理类
 */
public class ConfigManager {
    private ConfigManager(){plugin = ElysiaGrade.getPlugin(ElysiaGrade.class);}
    private static final ConfigManager instance = new ConfigManager();
    public static ConfigManager getInstance() {
        return instance;
    }
    public ConfigData getConfigData(){return configData;}
    private final ElysiaGrade plugin;
    private ConfigData configData;
    // 加载配置文件
    public void loadConfig() {
        configData = null;
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        HashMap<String, String> messages = new HashMap<>();
        HashMap<String, Integer> monsters = new HashMap<>();
        HashMap<String, String> experience = new HashMap<>();
        for (String key : config.getConfigurationSection("messages").getKeys(false))
            messages.put(key, config.getString("messages." + key));
        for (String key : config.getConfigurationSection("monsters").getKeys(false))
            monsters.put(key, config.getInt("monsters." + key));
        for (String key : config.getConfigurationSection("experience").getKeys(false))
            experience.put(key, config.getString("experience." + key));
        configData = new ConfigData(
                config.getBoolean("debug"),
                config.getString("prefix"),
                config.getString("type"),
                experience,
                config.getInt("defaultLevel"),
                config.getInt("save_timer"),
                config.getBoolean("save_tips"),
                messages
        );
        logConfigInfoIfDebug(configData);
    }
    // 打印配置信息
    private void logConfigInfoIfDebug(ConfigData configData) {
        if (configData.isDebug()) {
            plugin.getLogger().info("§eDebug: §a" + configData.isDebug());
            plugin.getLogger().info("§ePrefix: §a" + configData.getPrefix());
            plugin.getLogger().info("§eType: §a" + configData.getType());
            plugin.getLogger().info("§eExperience:");
            for (String key : configData.getExperience().keySet())
                plugin.getLogger().info("§e" + key + ": §a" + configData.getMessages().get(key));
            plugin.getLogger().info("§eDefault Level: §a" + configData.getDefaultLevel());
            plugin.getLogger().info("§eSave Timer: §a" + configData.getSaveTimer());
            plugin.getLogger().info("§eSave Tips: §a" + configData.isSaveTips());
            plugin.getLogger().info("§eMessages:");
            for (String key : configData.getMessages().keySet())
                plugin.getLogger().info("§e" + key + ": §a" + configData.getMessages().get(key));
            plugin.getLogger().info("§eMonsters:");
        }
    }
}
