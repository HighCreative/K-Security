package cloud.swiftnode.ksecurity.module.kspam.abstraction.checker;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.Info;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamChecker;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class FirstKickChecker extends SpamChecker {

    public FirstKickChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(info.getName());
        if (player == null || !Config.isFirstLoginKick() ||
                player.hasPlayedBefore()) {
            lastInfo = "";
            return Result.PASS;
        }
        final String name = lastInfo = player.getName();
        if (!StaticStorage.FIRST_KICK_CACHED_SET.contains(name)) {
            StaticStorage.FIRST_KICK_CACHED_SET.add(name);
            Static.runTaskLaterAsync(() -> StaticStorage.FIRST_KICK_CACHED_SET.remove(name), 20 * 30);
            return Result.DENY;
        }
        StaticStorage.FIRST_KICK_CACHED_SET.remove(name);
        return Result.PASS;
    }

    @Override
    public boolean isCaching() {
        return false;
    }

    @Override
    public Lang.MessageBuilder denyMsg() {
        return Lang.FIRST_LOGIN_KICK.builder();
    }
}
