package de.cuuky.taskz;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface ScheduledTask<I, O> extends Task<I, O> {
    ScheduledExecutorService SCHEDULED_EXECUTOR = Executors.newScheduledThreadPool(1);

}
