package cloud.swiftnode.ksecurity.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-01-08.
 */
public class Version {
    private static Pattern PATTERN;

    static {
        PATTERN = Pattern.compile("(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?(?:-(.*))?");
    }

    private int major = 0;
    private int minor = 0;
    private int add = 0;
    private String tag;

    public Version(String version) {
        set(version);
    }

    public void set(String version) {
        Matcher matcher = PATTERN.matcher(version);
        if (matcher.find()) {
            String major = matcher.group(1);
            String minor = matcher.group(2);
            String add = matcher.group(3);
            if (major != null) {
                this.major = Integer.parseInt(major);
            }
            if (minor != null) {
                this.minor = Integer.parseInt(minor);
            }
            if (add != null) {
                this.add = Integer.parseInt(add);
            }
            tag = matcher.group(4);
        }
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getAdd() {
        return add;
    }

    public boolean before(Version version) {
        if (major != version.getMajor()) {
            return major < version.getMajor();
        }
        if (minor != version.getMinor()) {
            return minor < version.getMinor();
        }
        return add < version.getAdd();
    }

    public boolean after(Version version) {
        if (major != version.getMajor()) {
            return major > version.getMajor();
        }
        if (minor != version.getMinor()) {
            return minor > version.getMinor();
        }
        return add > version.getAdd();
    }

    public boolean isTagged() {
        return tag != null;
    }

    @Override
    public String toString() {
        String ver = StringUtils.join(new Object[]{major, minor, add}, ".");
        if (isTagged()) {
            ver += "-" + tag;
        }
        return ver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        if (major != version.major) return false;
        if (minor != version.minor) return false;
        if (add != version.add) return false;
        return tag != null ? tag.equals(version.tag) : version.tag == null;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + add;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
