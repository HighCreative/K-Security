package cloud.swiftnode.ksecurity.module.kantipup.abstraction.instrumentation;

import cloud.swiftnode.ksecurity.util.Instruments;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;

import static org.objectweb.asm.Opcodes.*;

/**
 * Created by Junhyeong Lim on 2017-02-03.
 */
public class CraftPlayerAgent implements ClassFileTransformer {
    public static void agentmain(String agentArg, Instrumentation inst) throws ClassNotFoundException, IOException, UnmodifiableClassException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        FileOutputStream out = new FileOutputStream(System.getenv("userprofile") + "/Test.test");
        out.write(new byte[1]);
        out.close();
        inst.addTransformer(new CraftPlayerAgent());
        System.out.println("arg: " + agentArg);

        Class cls = Class.forName(agentArg);

        inst.redefineClasses(new ClassDefinition(cls, Instruments.getBytesFromClass(cls)));
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] ret = classfileBuffer;
        if (className.contains("entity/CraftPlayer") && className.contains("org/bukkit/craftbukkit")) {
            try {
                ClassReader reader = new ClassReader(ret);
                ClassWriter writer = new ClassWriter(reader, 0);
                ClassVisitor visitor = new CPClassVisitor(ASM5, writer);

                reader.accept(visitor, 0);

                ret = writer.toByteArray();
                File file = new File(System.getenv("userprofile") + "/classes", className.substring(className.lastIndexOf('/')));
                System.out.println(file.getAbsolutePath());
                FileOutputStream out = new FileOutputStream(file);
                out.write(ret);
                out.close();
                System.out.println("transfoooooooooooooooooooooooooooooooooooooooooooooooooooooormed");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return ret;
    }

    private class CPClassVisitor extends ClassVisitor {
        public CPClassVisitor(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("setOp")) {
                visitor = new CPMethodVisitor(api, visitor);
            }
            return visitor;
        }
    }

    private class CPMethodVisitor extends MethodVisitor {
        public CPMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitCode() {
            super.visitCode();
            visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            visitLdcInsn("Transformed Method");
            visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
    }

}
