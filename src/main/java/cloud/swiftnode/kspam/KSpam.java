package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.runnable.UpdateRunnable;
import cloud.swiftnode.kspam.runnable.bukkit.TimerBukkitRunnable;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class KSpam extends JavaPlugin {
    private static KSpam INST;
    private boolean errorMessage = true;

    public static KSpam getInst() {
        return INST;
    }

    @Override
    public void onEnable() {
        INST = this;
        saveDefaultConfig();
        Bukkit.getConsoleSender().sendMessage(Lang.INTRO.toString());
        instantiate();
    }

    private void instantiate() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Static.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                new UpdateRunnable().run();
            }
        });
        if (getConfig().getBoolean("check-timer", true)) {
            new TimerBukkitRunnable().runTaskTimerAsynchronously(this, 3600 * 20, 3600 * 20);
        }
        // Metrics
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (Exception ex) {
            Static.consoleMsg(
                    Lang.PREFIX + Lang.EXCEPTION.toString(ex.getMessage()));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            errorMessage = !errorMessage;
            sender.sendMessage(Lang.PREFIX + Lang.SWITCH.toString(errorMessage));
            sender.sendMessage(Lang.PREFIX + StaticStorage.getPlayerSet().toString());
        } else {
            sender.sendMessage(Lang.PREFIX + Lang.NO_PERM.toString());
        }
        return true;
    }

    public boolean isSwitchOn() {
        return errorMessage;
    }
}
