package de.cuuky.taskz.observe;

import de.cuuky.taskz.Task;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Stream;

public class OrderedObserverManager<I> implements ObserverManager<I> {

    private final SortedSet<Registration<? extends I>> tasks;

    public OrderedObserverManager() {
        this.tasks = Collections.synchronizedSortedSet(new TreeSet<>());
    }

    private static Class<?>[] firstParameterSmart(Object function) {
        String functionClassName = function.getClass().getName();
        int lambdaMarkerIndex = functionClassName.indexOf("$$Lambda$");
        String declaringClassName = functionClassName.substring(0, lambdaMarkerIndex);
        Class<?> declaringClass;
        try {
            declaringClass = Class.forName(declaringClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to find lambda's parent class " + declaringClassName);
        }

        return Arrays.stream(declaringClass.getDeclaredMethods())
                .filter(Method::isSynthetic).findFirst().map(Method::getParameterTypes)
                .orElseThrow(() -> new IllegalStateException("Unable to find lambda's synthetic method")).clone();
    }

    @SuppressWarnings("unchecked")
    private <T extends I> Class<T> parseEventClass(Task<T, ?> task) {
        // First check if the task is a lambda expression or an anonymous class
        if (task.getClass().isSynthetic()) {
            return (Class<T>) firstParameterSmart(task)[0];
        }
        return (Class<T>) ((ParameterizedType) task.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
    }

    private int parsePriority(Observer<?> task) {
        ObserverMeta priority = task.getClass().getAnnotation(ObserverMeta.class);
        return priority == null ? ExecutionOrder.DEFAULT : priority.value();
    }

    private <T extends I> Registration<T> toRegistration(Observer<T> task) {
        return new Registration<>(UUID.randomUUID(), this.parseEventClass(task), task, this.parsePriority(task));
    }

    @Override
    public <T extends I> Registration<T> observe(Observer<T> task) {
        Registration<T> registration = this.toRegistration(task);
        return !this.tasks.add(registration) ? null : registration;
    }

    @Override
    public boolean unobserve(UUID uuid) {
        return this.tasks.remove(new Registration<I>(uuid, null, null, 0));
    }

    @Override
    public Boolean execute(I input) {
        Class<?> clazz = input.getClass();
        Stream<Registration<? extends I>> tasks = this.tasks.stream()
                .filter(task -> task.clazz().isAssignableFrom(clazz));
        return tasks.map(task -> task.execute(input)).allMatch(b -> b == null || b);
    }
}
