package de.cuuky.taskz;

public interface StatefulTask<I, O> extends Task<I, O> {

    boolean cancel(boolean interrupt);

    boolean isRunning();

}
