package main.elysiagrade.filemanager;

import main.elysiagrade.ElysiaGrade;
import main.elysiagrade.filemanager.data.PlayerData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

/**
 * 玩家数据管理
 **/
public class PlayerManager {
    private PlayerManager() {plugin = ElysiaGrade.getPlugin(ElysiaGrade.class);}
    private final static PlayerManager instance = new PlayerManager();
    public static PlayerManager getInstance() {
        return instance;
    }
    private final HashMap<UUID, PlayerData> playerDataHashMap = new HashMap<>();
    private final ElysiaGrade plugin;
    public PlayerData getPlayerData(UUID uuid) {
        if (playerDataHashMap.containsKey(uuid)) {
            return playerDataHashMap.get(uuid);
        }
        return null;
    }
    public void setPlayerData(UUID uuid, PlayerData playerData) {
        playerDataHashMap.put(uuid, playerData);
    }
    public void loadPlayerData() {
        findAllYmlFiles(new File(plugin.getDataFolder() + "/PlayerData"));
    }
    public void savePlayerData() {

        for(UUID uuid : playerDataHashMap.keySet()){
            Path playerDataPath = plugin.getDataFolder().toPath().resolve("PlayerData").resolve(uuid.toString() + ".yml");
            try {
                Files.createDirectories(playerDataPath.getParent());
                YamlConfiguration yamlConfiguration = new YamlConfiguration();
                yamlConfiguration.set("uuid", uuid.toString());
                yamlConfiguration.set("level", playerDataHashMap.get(uuid).getLevel());
                yamlConfiguration.set("experience", playerDataHashMap.get(uuid).getExperience());
                try {
                    yamlConfiguration.save(playerDataPath.toFile());
                } catch (IOException e) {
                    throw new UncheckedIOException("Failed to save player data.", e);
                }
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to save player data.", e);
            }
        }
    }
    private void findAllYmlFiles(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是文件夹则递归查找
                    findAllYmlFiles(file);
                } else if (file.getName().endsWith(".yml")) {
                    // 如果是YML文件则加入结果列表
                    File playerDataFolder = new File(folder + "/" + file.getName());
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(playerDataFolder);
                    loadPlayerData(config);
                }
            }
        }
    }
    private void loadPlayerData(YamlConfiguration config) {
        PlayerData playerData = new PlayerData(
                config.getInt("level"),
                config.getDouble("experience")
        );
        playerDataHashMap.put(UUID.fromString(config.getString("uuid")), playerData);
    }
}
