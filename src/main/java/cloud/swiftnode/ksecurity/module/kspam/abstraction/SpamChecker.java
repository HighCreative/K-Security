package cloud.swiftnode.ksecurity.module.kspam.abstraction;

import cloud.swiftnode.ksecurity.abstraction.Named;
import cloud.swiftnode.ksecurity.util.Lang;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamChecker implements Checker, Named {
    protected Info info;
    protected String lastInfo;

    public SpamChecker(Info info) {
        this.info = info;
    }

    @Override
    public boolean check() {
        try {
            return spamCheck() == Result.DENY;
        } catch (Exception ex) {
            // Ignore
        }
        return false;
    }

    public abstract Result spamCheck() throws Exception;

    public boolean isCaching() {
        return true;
    }

    public Lang.MessageBuilder denyMsg() {
        return Lang.DENY.builder();
    }

    public boolean onlyFirst() {
        return false;
    }

    public String getLastInfo() {
        return lastInfo;
    }

    public void setLastInfo(String lastInfo) {
        this.lastInfo = lastInfo;
    }

    public enum Result {
        PASS,
        DENY,
        ERROR,
        FORCE_PASS
    }
}
