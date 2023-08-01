package com.mazebert.simulation;

import com.mazebert.simulation.util.CardComparator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static com.mazebert.util.ClassScanner.getClasses;
import static org.assertj.core.api.Assertions.assertThat;

public class StrictfpTest {

    private static final double JAVA_VERSION = Double.parseDouble(System.getProperty("java.specification.version", "0"));
    private static final Class[] WHITELIST = {CardComparator.class};

    @Test
    void allSimulationClassesUseStrictFloatingPointMode() throws IOException, ClassNotFoundException {
        if (JAVA_VERSION >= 17) {
            return; // strictfp is the default since Java 17 and the modifier got removed.
        }

        int failures = 0;

        Class[] classes = getClasses("com.mazebert.simulation", false);
        assertThat(classes).isNotEmpty();

        for (Class clazz : classes) {
            if (!isStrict(clazz)) {
                System.err.println("please add the strictfp modifier to .(" + clazz.getSimpleName() + ".java:1)");
                failures++;
            }
        }

        assertThat(failures).isEqualTo(0);
    }

    private boolean isStrict(Class clazz) {
        if (clazz.isInterface()) {
            return true;
        }

        if (isWhiteListed(clazz)) {
            return true;
        }

        for (Method method : clazz.getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.contains("ajc$preClinit") || methodName.contains("$jacocoInit") || methodName.contains("access$")) {
                continue;
            }
            if (Modifier.isAbstract(method.getModifiers())) {
                continue;
            }
            if (Modifier.isVolatile(method.getModifiers())) {
                continue;
            }

            if (!Modifier.isStrict(method.getModifiers())) {
                System.err.println("Method " + methodName + " is not strict (Modifiers: " + Modifier.toString(method.getModifiers()) + ")");
                return false;
            }
        }
        return true;
    }

    private boolean isWhiteListed(Class clazz) {
        for (Class whiteListedClass : WHITELIST) {
            if (clazz == whiteListedClass) {
                return true;
            }
        }
        return false;
    }
}