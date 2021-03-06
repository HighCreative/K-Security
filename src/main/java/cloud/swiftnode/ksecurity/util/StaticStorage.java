package cloud.swiftnode.ksecurity.util;


import cloud.swiftnode.ksecurity.abstraction.collection.LowerCaseLinkedSet;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class StaticStorage {
    public static Set<String> cachedSet = new LowerCaseLinkedSet();
    public static final Set<String> FIRST_KICK_CACHED_SET = new LowerCaseLinkedSet();
    public static final Set<String> NET_ESCAPE_SET = new LowerCaseLinkedSet();
    public static boolean firewallMode = false;
    private static Map<ClassLoader, Plugin> cachedLoaderPluginMap;
    private static Version currVer;
    private static Version newVer;

    public static Version getCurrVer() {
        if (currVer == null) {
            currVer = new Version(Static.getVersion());
        }
        return currVer;
    }

    public static void setCurrVer(Version currVer) {
        StaticStorage.currVer = currVer;
    }

    public static Version getNewVer() {
        if (newVer == null) {
            newVer = new Version("0.0");
        }
        return newVer;
    }

    public static void setNewVer(Version newVer) {
        StaticStorage.newVer = newVer;
    }

    public synchronized static Map<ClassLoader, Plugin> getCachedLoaderPluginMap() {
        if (cachedLoaderPluginMap == null) {
            cachedLoaderPluginMap = new HashMap<>();
            for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                StaticStorage.cachedLoaderPluginMap.put(plugin.getClass().getClassLoader(), plugin);
            }
        }
        return cachedLoaderPluginMap;
    }
}
