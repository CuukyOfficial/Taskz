package de.cuuky.taskz.observe;

import de.cuuky.taskz.Task;
import de.cuuky.taskz.util.ClassUtil;

public abstract class AbstractObserverManager<I> implements ObserverManager<I> {

    @SuppressWarnings("unchecked")
    protected  <T extends I> Class<T> parseEventClass(Task<T, ?> task) {
        // First check if the task is a lambda expression or an anonymous class
        if (task.getClass().isSynthetic()) {
            return (Class<T>) ClassUtil.firstParameterSmart(task);
        }
        return ClassUtil.parseFirstParameter(task);
    }

    @Override
    public <T> Task<T, Boolean> wrap(Task<T, I> task) {
        return ((T input) -> this.execute(task.execute(input)));
    }
}
