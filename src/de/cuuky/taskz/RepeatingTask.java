package de.cuuky.taskz;

import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;

public interface RepeatingTask<I> extends ScheduledTask<Supplier<I>, ScheduledFuture<?>> {

    boolean isRunning();

    boolean cancel(boolean interrupt);

}
