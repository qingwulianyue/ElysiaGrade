package main.elysiagrade.filemanager;

import main.elysiagrade.ElysiaGrade;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * 文件监听器
 */
public class FileListener {
    private static final long POLL_INTERVAL = TimeUnit.SECONDS.toMillis(1);
    public static void startWatching(File rootDir) {
        FileAlterationObserver observer = new FileAlterationObserver(rootDir);
        observer.addListener(new FileAlterationListenerAdaptor() {
            @Override
            public void onFileChange(File file) {
                if (file.getName().equals("reward.yml") || file.getName().equals("config.yml"))
                    ElysiaGrade.getPlugin(ElysiaGrade.class).getLogger().info("文件更改: " + file.getAbsolutePath());
                if (file.getName().equals("reward.yml")) {
                    ElysiaGrade.getRewardManager().loadReward();
                } else if (file.getName().equals("config.yml"))
                   ElysiaGrade.getConfigManager().loadConfig();
            }
        });

        FileAlterationMonitor monitor = new FileAlterationMonitor(POLL_INTERVAL, observer);
        try {
            monitor.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
