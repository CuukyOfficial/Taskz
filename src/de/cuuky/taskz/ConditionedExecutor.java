package de.cuuky.taskz;

import java.util.Optional;
import java.util.function.Predicate;

public class ConditionedExecutor<I, O> implements Task<I, Optional<O>> {

    private final Predicate<I> condition;
    private final Task<I, O> task;

    public ConditionedExecutor(Predicate<I> condition, Task<I, O> task) {
        this.condition = condition;
        this.task = task;
    }

    @Override
    public Optional<O> execute(I input) {
        return condition.test(input) ? Optional.of(task.execute(input)) : Optional.empty();
    }
}
