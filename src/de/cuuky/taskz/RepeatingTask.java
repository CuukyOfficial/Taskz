package de.cuuky.taskz;

import de.cuuky.taskz.observe.ObserverManager;

import java.util.function.Supplier;

public interface RepeatingTask<I,O> extends ScheduledTask<Supplier<I>, ObserverManager<O>> {

    boolean isRunning();

    boolean cancel(boolean interrupt);

}
