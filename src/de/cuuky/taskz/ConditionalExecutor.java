package de.cuuky.taskz;

import java.util.Optional;

public class ConditionalExecutor<I, O> implements OptionalTask<I, O> {

    private final Task<I, O> task;
    private final Task<I, Boolean> condition;

    public ConditionalExecutor(Task<I, O> task, Task<I, Boolean> condition) {
        this.task = task;
        this.condition = condition;
    }

    @Override
    public Optional<O> execute(I input) {
        return this.condition.execute(input) ? Optional.of(this.task.execute(input)) : Optional.empty();
    }
}
