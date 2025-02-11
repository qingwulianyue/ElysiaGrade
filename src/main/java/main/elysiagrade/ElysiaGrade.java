package main.elysiagrade;

import main.elysiagrade.command.CommandManager;
import main.elysiagrade.command.CommandTabComplete;
import main.elysiagrade.filemanager.ConfigManager;
import main.elysiagrade.filemanager.FileListener;
import main.elysiagrade.filemanager.PlayerManager;
import main.elysiagrade.filemanager.RewardManager;
import main.elysiagrade.hook.PlaceholderAPIHook;
import main.elysiagrade.listeneer.ElysiaGradeEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
/**
 * @author Elysia
 * 玩家等级插件
 **/

public final class ElysiaGrade extends JavaPlugin {
    private static ConfigManager configManager;
    private static PlayerManager playerManager;
    private static RewardManager rewardManager;
    public static ConfigManager getConfigManager() {
        return configManager;
    }
    public static PlayerManager getPlayerManager() {
        return playerManager;
    }
    public static RewardManager getRewardManager() {
        return rewardManager;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        createDefaultFile();
        configManager = ConfigManager.getInstance();
        playerManager = PlayerManager.getInstance();
        rewardManager = RewardManager.getInstance();
        configManager.loadConfig();
        playerManager.loadPlayerData();
        rewardManager.loadReward();
        Bukkit.getPluginCommand("ElysiaGrade").setExecutor(new CommandManager());
        Bukkit.getPluginCommand("ElysiaGrade").setTabCompleter(new CommandTabComplete());
        Bukkit.getPluginManager().registerEvents(new ElysiaGradeEvent(), this);
        checkDepend();
        startAutomaticSavePlayerDataTask();
        FileListener.startWatching(getDataFolder());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        playerManager.savePlayerData();
    }

    private void createDefaultFile(){
        saveDefaultConfig();
        createDirectoryIfNotExists(getDataFolder().toPath().resolve("PlayerData"));
        Path filePath = getDataFolder().toPath().resolve("reward.yml");
        if (!Files.exists(filePath)) {
            try (InputStream resourceStream = getResourceAsStream()) {
                Files.copy(resourceStream, filePath);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to create default file.", e);
            }
        }
    }
    private void createDirectoryIfNotExists(Path directoryPath) {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
            } catch (IOException e) {
                throw new UncheckedIOException("Failed to create directory.", e);
            }
        }
    }
    private InputStream getResourceAsStream() {
        InputStream resourceStream = getClass().getResourceAsStream("/reward.yml");
        if (resourceStream == null) {
            throw new RuntimeException("Resource 'reward.yml' not found in classpath.");
        }
        return resourceStream;
    }
    /**
     * 自动保存玩家数据
     **/
    private void startAutomaticSavePlayerDataTask() {
        long ticks = configManager.getConfigData().getSaveTimer() * 20L;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (configManager.getConfigData().isSaveTips())
                    getLogger().info("§e开始保存玩家数据");
                playerManager.savePlayerData();
            }
        }.runTaskTimerAsynchronously(this, 0L, ticks);
    }
    private void checkDepend(){
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            getLogger().info("§a检测到 PlaceholderAPI，正在注册变量");
            new PlaceholderAPIHook().register();
        } else {
            getLogger().warning("§c未检测到 PlaceholderAPI，变量将无法使用");
        }
    }
}
