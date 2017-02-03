package cloud.swiftnode.ksecurity.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.abstraction.manager.KPluginManager;
import cloud.swiftnode.ksecurity.module.kantipup.abstraction.instrumentation.CraftPlayerAgent;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.network.KProxySelector;
import cloud.swiftnode.ksecurity.util.Instruments;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import com.ea.agentloader.AgentLoader;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.net.ProxySelector;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class InjectionProcessor implements Processor {

    @Override
    public boolean process() {
        try {
            proxySelectInjection();
            pluginManagerInjection();
            craftPlayerInstrumentation();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return true;
    }

    private void pluginManagerInjection() throws NoSuchFieldException, IllegalAccessException {
        Server server = Bukkit.getServer();

        Reflections.setDecField(server.getClass(), server, "pluginManager",
                new KPluginManager(Bukkit.getPluginManager()));
    }

    private void proxySelectInjection() throws ClassNotFoundException {
        ProxySelector.setDefault(
                new KProxySelector(ProxySelector.getDefault()));
    }

    private void craftPlayerInstrumentation() throws Exception {
        AgentLoader.loadAgent(Instruments.generateAgent(CraftPlayerAgent.class).getAbsolutePath(), Instruments.getOBCClassName("entity.CraftPlayer"));
//        URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
//        method.setAccessible(true);
//        method.invoke(loader, Instruments.generateAgent(CraftPlayerAgent.class).toURI().toURL());
    }
}
