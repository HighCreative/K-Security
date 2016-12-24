package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.reflection.ClassProbe;
import cloud.swiftnode.kspam.reflection.TestEscape;
import cloud.swiftnode.kspam.reflection.Testable;
import cloud.swiftnode.kspam.storage.ReflectionStorage;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.storage.VersionStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Version;
import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by EntryPoint on 2016-12-24.
 */
public class SpamCheckerTest {
    @Test
    @SuppressWarnings("unchecked")
    public void run() throws Exception {
        StaticStorage.setVersionStorage(new VersionStorage(new Version("-1"), new Version("-1")));
        ClassProbe probe = new ClassProbe(
                new ReflectionStorage(ClassProbe.class.getClassLoader(),
                        "cloud.swiftnode.kspam.abstraction"));
        SpamStorage storage = new SpamStorage(Result.FALSE, "59.20.43.105");
        for (Class clazz : probe.getClassesWithAnnotation(Testable.class)) {
            if (SpamChecker.class.isAssignableFrom(clazz) && !SpamChecker.class.equals(clazz) && !clazz.isAnnotationPresent(TestEscape.class)) {
                System.out.println(clazz.toString());
                Constructor constructor = clazz.getConstructor(SpamStorage.class);
                SpamChecker checker = (SpamChecker) constructor.newInstance(storage);
                checker.check();
                System.out.println(storage.getResult());
                assertNotEquals(storage.getResult(), Result.ERROR);
            }
        }
    }
}
