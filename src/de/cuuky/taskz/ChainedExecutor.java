package de.cuuky.taskz;

public class ChainedExecutor<I, M, O> implements  Task<I, O> {

    private final Task<I, M> first;
    private final Task<M, O> second;

    public ChainedExecutor(Task<I, M> first, Task<M, O> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public O execute(I input) {
        return this.second.execute(this.first.execute(input));
    }
}
