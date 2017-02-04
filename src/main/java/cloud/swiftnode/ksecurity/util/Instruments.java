package cloud.swiftnode.ksecurity.util;

import cloud.swiftnode.ksecurity.KSecurity;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * Created by Junhyeong Lim on 2017-02-01.
 */
public class Instruments {
    public static byte[] getBytesFromClass(Class cls) throws IOException {
        return getBytesFromStream(getStreamFromClass(cls));

    }

    public static byte[] getBytesFromStream(InputStream stream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[65536];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    public static InputStream getStreamFromClass(Class cls) {
        if (KSecurity.inst != null) {
            return KSecurity.inst.getResource(toClassName(cls));
        }
        return ClassLoader.getSystemResourceAsStream(toClassName(cls));
    }

    public static String toClassName(Class clazz) {
        return clazz.getName().replaceAll("\\.", "/") + ".class";
    }

    public static String getOBCClassName(String children) throws NoSuchFieldException, IllegalAccessException {
        String str = Bukkit.getServer().getClass().getName();
        return str.substring(0, str.indexOf(".CraftServer")) + "." + children;
    }

    public static File generateAgent(Class agent, Class... resources) throws IOException {
        File jar = File.createTempFile("agent", ".jar");
//        jar.deleteOnExit();
        System.out.println("Path: " + jar.getAbsolutePath());

        Manifest manifest = new Manifest();
        Attributes attrs = manifest.getMainAttributes();

        attrs.put(Attributes.Name.MANIFEST_VERSION, "1.0");
        attrs.put(new Attributes.Name("Agent-Class"), agent.getName());
        attrs.put(new Attributes.Name("Can-Retransform-Classes"), "true");
        attrs.put(new Attributes.Name("Can-Redefine-Classes"), "true");
        try {
            attrs.put(new Attributes.Name("Boot-Class-Path"), change(getPluginPath()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        JarOutputStream out = new JarOutputStream(new FileOutputStream(jar), manifest);

        out.putNextEntry(new JarEntry(toClassName(agent)));
        out.write(getBytesFromClass(agent));
        out.closeEntry();

        for (Class resource : resources) {
            String clsName = toClassName(resource);
            out.putNextEntry(new JarEntry(clsName));
            out.write(getBytesFromClass(resource));
            out.closeEntry();
        }

        out.close();

        return jar;
    }

    public static String getPluginPath() throws URISyntaxException {
        return getStrLocFromClass(Instruments.class);
    }

    public static String getBukkitPath() throws URISyntaxException {
        return getStrLocFromClass(JavaPlugin.class);
    }

    private static String getStrLocFromClass(Class cls) throws URISyntaxException {
        return new File(cls.getProtectionDomain().getCodeSource().getLocation().toURI()).toString();
    }

    private static String change(String str) {
        return "/" + str.replace("\\", "/").replace(" ", "%20");
    }
}
