package com.mazebert.simulation;

import com.mazebert.simulation.usecases.Usecase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static com.mazebert.util.ClassScanner.getClasses;
import static org.assertj.core.api.Assertions.assertThat;

public class StrictfpTest {

    @Test
    void allSimulationClassesUseStrictFloatingPointMode() throws IOException, ClassNotFoundException {
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
        if (clazz == Usecase.class || clazz.isInterface()) {
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

            if (!Modifier.isStrict(method.getModifiers())) {
                System.err.println("Method " + methodName + " is not strict");
                return false;
            }
        }
        return true;
    }
}