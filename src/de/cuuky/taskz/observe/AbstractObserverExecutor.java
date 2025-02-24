package de.cuuky.taskz.observe;

import de.cuuky.taskz.Task;
import de.cuuky.taskz.util.ClassUtil;

import java.util.stream.Stream;

public abstract class AbstractObserverExecutor<I> implements ObserverExecutor<I> {

    @SuppressWarnings("unchecked")
    protected <T extends I> Class<T> parseEventClass(Task<T, ?> task) {
        // First check if the task is a lambda expression or an anonymous class
        if (task.getClass().isSynthetic()) {
            return (Class<T>) ClassUtil.firstParameterSmart(task);
        }
        return ClassUtil.parseFirstParameter(task);
    }

    protected boolean executeTasks(Stream<Registration<? extends I>> tasks, I input) {
        return tasks.map(task -> task.execute(input)).allMatch(b -> b == null || b);
    }

    @Override
    public <T> Task<T, Boolean> wrap(Task<T, I> task) {
        return ((T input) -> this.execute(task.execute(input)));
    }
}
