package de.cuuky.taskz.observe;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Stream;

public class OrderedObserverManager<I> extends AbstractObserverManager<I> {

    private final SortedSet<Registration<? extends I>> tasks;

    public OrderedObserverManager() {
        this.tasks = Collections.synchronizedSortedSet(new TreeSet<>());
    }

    private int parsePriority(Observer<?> task) {
        ObserverMeta priority = task.getClass().getAnnotation(ObserverMeta.class);
        return priority == null ? ExecutionOrder.DEFAULT : priority.value();
    }

    private <T extends I> Registration<T> toRegistration(Observer<T> task) {
        return new Registration<>(UUID.randomUUID(), System.nanoTime(), this.parseEventClass(task), task, this.parsePriority(task));
    }

    @Override
    public <T extends I> Registration<T> observe(Observer<T> task) {
        Registration<T> registration = this.toRegistration(task);
        return !this.tasks.add(registration) ? null : registration;
    }

    @Override
    public boolean unobserve(UUID uuid) {
        return this.tasks.remove(new Registration<I>(uuid, 0, null, null, 0));
    }

    @Override
    public Boolean execute(I input) {
        Class<?> clazz = input.getClass();
        Stream<Registration<? extends I>> tasks = this.tasks.stream()
                .filter(task -> task.clazz().isAssignableFrom(clazz));
        return tasks.map(task -> task.execute(input)).allMatch(b -> b == null || b);
    }
}
