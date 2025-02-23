package de.cuuky.taskz.state;

import de.cuuky.taskz.Task;

public interface StatefulTask<I, O> extends Task<I, O> {

    O stop(I input);

}
