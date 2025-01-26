package main.elysiagrade.filemanager.data;

import java.time.LocalDate;

public class PlayerData {
    private final int level;
    private final double experience;
    private final double daily_experience;
    private final LocalDate update_time;
    public PlayerData(int level, double experience, double dailyExperience, LocalDate updateTime) {
        this.level = level;
        this.experience = experience;
        daily_experience = dailyExperience;
        update_time = updateTime;
    }
    public int getLevel() {
        return level;
    }
    public double getExperience() {
        return experience;
    }
    public double getDaily_experience() {
        return daily_experience;
    }
    public LocalDate getUpdate_time() {
        return update_time;
    }
}
