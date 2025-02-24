package de.cuuky.taskz;

import java.util.stream.Stream;

public class MultiExecutor<I, O> implements Task<I, Stream<O>> {

    private final Stream<Task<I, O>> tasks;

    public MultiExecutor(Stream<Task<I, O>> tasks) {
        this.tasks = tasks;
    }

    @Override
    public Stream<O> execute(I input) {
        return tasks.map(task -> task.execute(input));
    }
}
