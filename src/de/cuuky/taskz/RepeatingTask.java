package de.cuuky.taskz;

import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;

public interface RepeatingTask<I> extends StatefulTask<Supplier<I>, ScheduledFuture<?>>,
        ScheduledTask<Supplier<I>, ScheduledFuture<?>> {

}
