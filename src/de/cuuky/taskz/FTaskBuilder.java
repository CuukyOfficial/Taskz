package de.cuuky.taskz;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FTaskBuilder<I, O> implements Task<I, O> {

    private Function<Task<I, O>, Task<I, O>> task;

    public FTaskBuilder(Function<I, O> function) {
        this.task = (task) -> function::apply;
    }

    public RTaskBuilder<Supplier<I>, ScheduledFuture<?>> schedule(long timeout) {
        return new RTaskBuilder<>(new ScheduledExecutor<>(this.task, timeout));
    }

    public RTaskBuilder<I, Optional<O>> check(Task<I, Boolean> condition) {
        return new RTaskBuilder<>(new ConditionalExecutor<>(this.task, condition));
    }

    public RTaskBuilder<I, Future<O>> delay(long timeout) {
        return new RTaskBuilder<>(new DelayedExecutor<>(this.task, timeout));
    }

    @Override
    public O execute(I input) {
        return this.task.execute(input);
    }
}