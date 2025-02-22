package de.cuuky.taskz;

import java.util.stream.Stream;

public class StreamExecutor<I> implements Task<I, Void> {

    private final Stream<Task<I, ?>> tasks;

    public StreamExecutor(Stream<Task<I, ?>> tasks) {
        this.tasks = tasks;
    }

    @Override
    public Void execute(I input) {
        tasks.forEach(task -> task.execute(input));
        return null;
    }
}
