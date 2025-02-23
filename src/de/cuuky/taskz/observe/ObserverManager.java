package de.cuuky.taskz.observe;

import de.cuuky.taskz.Task;

import java.util.UUID;

public interface ObserverManager<I> extends Task<I, Boolean> {

    <T extends I> Registration<T> observe(Observer<T> task);

    boolean unobserve(UUID uuid);

    <T> Task<T, Boolean> wrap(Task<T, I> task);

}
