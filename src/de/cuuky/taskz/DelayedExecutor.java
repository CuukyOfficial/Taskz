package de.cuuky.taskz;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DelayedExecutor<I, O> implements ScheduledTask<I, Future<O>> {

    private final Task<I, O> task;
    private final long timeout;

    public DelayedExecutor(Task<I, O> task, long timeout) {
        this.task = task;
        this.timeout = timeout;
    }

    @Override
    public Future<O> execute(I input) {
        return SCHEDULED_EXECUTOR.schedule(() -> task.execute(input), timeout, TimeUnit.MILLISECONDS);
    }
}
