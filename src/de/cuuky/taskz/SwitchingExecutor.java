package de.cuuky.taskz;

import java.util.Optional;

public class SwitchingExecutor<I, O> implements OptionalTask<I, O>, StatefulTask<I, Optional<O>> {

    private final StatefulTask<I, O> task;
    private final Task<I, Boolean> test;
    private boolean running;

    public SwitchingExecutor(StatefulTask<I, O> task, Task<I, Boolean> test) {
        this.task = task;
        this.test = test;
        this.running = false;
    }

    @Override
    public boolean cancel(boolean interrupt) {
        this.running = false;
        return this.task.cancel(interrupt);
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public Optional<O> execute(I input) {
        boolean result = this.test.execute(input);

        if (!result && this.running) {
            this.cancel(true);
        } else if (result && !this.running) {
            this.running = true;
            return Optional.of(this.task.execute(input));
        }

        return Optional.empty();
    }
}
