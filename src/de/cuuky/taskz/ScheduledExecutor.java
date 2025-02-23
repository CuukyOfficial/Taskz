package de.cuuky.taskz;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class ScheduledExecutor<I> implements RepeatingTask<I> {

    private final Task<I, ?> task;
    private final long schedule;
    private ScheduledFuture<?> future;

    public ScheduledExecutor(Task<I, ?> task, long schedule) {
        this.task = task;
        this.schedule = schedule;
    }

    @Override
    public ScheduledFuture<?> execute(Supplier<I> input) {
        if (this.isRunning()) throw new IllegalStateException("Cannot execute scheduled executor when already running");

        return this.future = SCHEDULED_EXECUTOR.scheduleAtFixedRate(() ->
                this.task.execute(input.get()), 0, this.schedule, TimeUnit.MILLISECONDS);
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
