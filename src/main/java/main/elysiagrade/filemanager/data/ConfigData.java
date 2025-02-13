package main.elysiagrade.filemanager.data;

import java.util.HashMap;

public class ConfigData {
    private final boolean debug;
    private final String prefix;
    private final String type;
    private final HashMap<String, String> experience;
    private final int daily_experience;
    private final int defaultLevel;
    private final int save_timer;
    private final boolean save_tips;
    private final HashMap<String, String> messages;
    private final HashMap<String, Integer> monsters;
    public ConfigData(boolean debug, String prefix, String type, HashMap<String, String> experience, int dailyExperience, int defaultLevel, int save_timer, boolean save_tips, HashMap<String, String> messages, HashMap<String, Integer> monsters) {
        this.debug = debug;
        this.prefix = prefix;
        this.type = type;
        this.experience = experience;
        daily_experience = dailyExperience;
        this.defaultLevel = defaultLevel;
        this.save_timer = save_timer;
        this.save_tips = save_tips;
        this.messages = messages;
        this.monsters = monsters;
    }

    public boolean isDebug() {
        return debug;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getType() {
        return type;
    }

    public HashMap<String, String> getExperience() {
        return experience;
    }
    public int getDaily_experience() {
        return daily_experience;
    }

    public int getDefaultLevel() {
        return defaultLevel;
    }

    public int getSaveTimer() {
        return save_timer;
    }

    public boolean isSaveTips() {
        return save_tips;
    }

    public HashMap<String, String> getMessages() {
        return messages;
    }

    public HashMap<String, Integer> getMonsters() {
        return monsters;
    }
}
