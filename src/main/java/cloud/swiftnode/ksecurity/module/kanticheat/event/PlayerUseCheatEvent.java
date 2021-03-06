package cloud.swiftnode.ksecurity.module.kanticheat.event;

import cloud.swiftnode.ksecurity.util.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class PlayerUseCheatEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private CheatType type;

    public PlayerUseCheatEvent(Player who, CheatType type) {
        super(who);
        this.type = type;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public CheatType getType() {
        return type;
    }

    public void setType(CheatType type) {
        this.type = type;
    }

    public enum CheatType {
        /**
         * 샵키퍼 아이템 복사
         */
        SHOPKEEPER(Config.SHOPKEEPER, "샵키퍼 복사"),

        /**
         * 취약 색코드 &k
         */
        CRASH_COLOR(Config.CRASH_COLOR, "취약 색코드"),

        /**
         * 트리거 플레이스홀더 사용
         */
        VARIABLE_TRIGGER(Config.VARIABLE_TRIGGER, "트리거"),

        /**
         * RPGITEM 아이템 복사
         */
        RPGITEM(Config.RPGITEM, "RPGITEM"),

        /**
         * 프리캠 복사
         */
        FREECAM(Config.FREECAM, "프리캠"),

        /**
         * PLAYERVAULT 복사
         */
        PLAYER_VAULT(Config.PLAYER_VAULT, "가상창고"),
        ;

        private final String key;
        private final String name;

        CheatType(String key, String name) {
            this.key = key;
            this.name = name;
        }

        public boolean isDeny() {
            return Config.getBoolean(key, true);
        }

        public String getName() {
            return name;
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
