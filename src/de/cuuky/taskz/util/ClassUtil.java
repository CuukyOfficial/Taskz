package de.cuuky.taskz.util;

import de.cuuky.taskz.Task;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

public final class ClassUtil {

    private ClassUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> parseFirstParameter(Task<T, ?> task) {
        return (Class<T>) ((ParameterizedType) task.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    public static <T> Class<?> firstParameterSmart(Task<T, ?> function) {
        String functionClassName = function.getClass().getName();
        int lambdaMarkerIndex = functionClassName.indexOf("$$Lambda$");
        String declaringClassName = functionClassName.substring(0, lambdaMarkerIndex);
        Class<?> declaringClass;
        try {
            declaringClass = Class.forName(declaringClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find lambda's parent class " + declaringClassName);
        }

        Method method = Arrays.stream(declaringClass.getDeclaredMethods())
                .filter(Method::isSynthetic)
                .findFirst().orElseThrow(() -> new IllegalStateException("Unable to find lambda's synthetic method"));

        return method.getParameterTypes()[method.getParameters().length - 1];
    }
}
