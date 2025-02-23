package de.cuuky.taskz;

import java.util.Optional;

public class ConditionalExecutor<I, O> implements Task<I, Optional<O>> {

    private final Task<I, Boolean> condition;
    private final Task<I, O> task;

    public ConditionalExecutor(Task<I, Boolean> condition, Task<I, O> task) {
        this.condition = condition;
        this.task = task;
    }

    @Override
    public Optional<O> execute(I input) {
        return condition.execute(input) ? Optional.of(task.execute(input)) : Optional.empty();
    }
}
