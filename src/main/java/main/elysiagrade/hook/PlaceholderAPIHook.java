package main.elysiagrade.hook;

import main.elysiagrade.ElysiaGrade;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook extends PlaceholderExpansion {
    /**
     * %ElysiaGrade_level%:玩家的当前等级
     * %ElysiaGrade_exp%:玩家的当前经验值
     * %ElysiaGrade_maxExp%:玩家的当前等级对应的最大经验值
     **/
    @Override
    public @org.jetbrains.annotations.NotNull String getIdentifier() {
        return "ElysiaGrade";
    }

    @Override
    public @org.jetbrains.annotations.NotNull String getAuthor() {
        return "Elysia";
    }

    @Override
    public @org.jetbrains.annotations.NotNull String getVersion() {
        return ElysiaGrade.getPlugin(ElysiaGrade.class).getDescription().getVersion();
    }
    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null || params == null || params.isEmpty()) return null;
        if (params.equals("level")) return getLevel(player);
        if (params.equals("exp")) return getExp(player);
        if (params.equals("maxExp")) return getMaxExp(player);
        return null;
    }
    private String getLevel(Player player) {
        return String.valueOf(ElysiaGrade.getPlayerManager().getPlayerData(player.getUniqueId()).getLevel());
    }
    private String getExp(Player player) {
        return String.valueOf(ElysiaGrade.getPlayerManager().getPlayerData(player.getUniqueId()).getExperience());
    }
    private String getMaxExp(Player player) {
        int level = ElysiaGrade.getPlayerManager().getPlayerData(player.getUniqueId()).getLevel();
        String formula = ElysiaGrade.getConfigManager().getConfigData().getExperience().replaceAll("level", String.valueOf(level));
        double maxExperience = new ExpressionBuilder(formula).build().evaluate();
        return String.valueOf(maxExperience);
    }
}
