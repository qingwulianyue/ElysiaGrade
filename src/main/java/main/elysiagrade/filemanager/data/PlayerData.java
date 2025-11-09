package main.elysiagrade.filemanager.data;

import java.time.LocalDate;

public class PlayerData {
    private final int level;
    private final double experience;
    public PlayerData(int level, double experience) {
        this.level = level;
        this.experience = experience;
    }
    public int getLevel() {
        return level;
    }
    public double getExperience() {
        return experience;
    }
}
