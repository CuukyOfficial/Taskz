package de.cuuky.taskz.observe;

import java.util.*;

public class FastObserverExecutor<I> extends AbstractObserverExecutor<I> {

    private final Map<Class<? extends I>, Set<Registration<? extends I>>> observers;

    public FastObserverExecutor() {
        this.observers = new HashMap<>();
    }

    @Override
    public <T extends I> Registration<T> observe(Observer<T> task) {
        Class<T> clazz = this.parseEventClass(task);
        Set<Registration<? extends I>> tasks = this.observers.computeIfAbsent(clazz, c -> new LinkedHashSet<>());
        Registration<T> registration = new Registration<>(UUID.randomUUID(), System.nanoTime(), clazz, task, 0);
        return tasks.add(registration) ? registration : null;
    }

    @Override
    public boolean unobserve(UUID uuid) {
        Registration<?> registration = new Registration<>(uuid, 0, null, null, 0);
        return this.observers.values().stream().anyMatch(tasks -> tasks.remove(registration));
    }

    @Override
    public Boolean execute(I input) {
        Class<?> clazz = input.getClass();
        Set<Registration<? extends I>> tasks = this.observers.getOrDefault(clazz, Collections.emptySet());
        return this.executeTasks(tasks.stream(), input);
    }
}
