package de.cuuky.taskz;

import de.cuuky.taskz.observe.OrderedObserverManager;
import de.cuuky.taskz.observe.ObserverManager;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ScheduledExecutor<I, O> implements RepeatingTask<I, O> {

    private final Task<I, O> task;
    private final long schedule;
    private ScheduledFuture<?> future;

    public ScheduledExecutor(Task<I, O> task, long schedule) {
        this.task = task;
        this.schedule = schedule;
    }

    @Override
    public ObserverManager<O> execute(Supplier<I> input) {
        if (this.isRunning()) throw new IllegalStateException("Already running");

        ObserverManager<O> registry = new OrderedObserverManager<>();

        this.future = SCHEDULED_EXECUTOR.scheduleAtFixedRate(() ->
                registry.execute(this.task.execute(input.get())), 0, this.schedule, TimeUnit.MILLISECONDS);

        return registry;
    }

    @Override
    public boolean isRunning() {
        return this.future != null && this.future.state() == Future.State.RUNNING;
    }

    @Override
    public boolean cancel(boolean interrupt) {
        return this.future != null && this.future.cancel(interrupt);
    }
}
