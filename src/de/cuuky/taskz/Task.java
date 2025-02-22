package de.cuuky.taskz;

public interface Task<I, O> {

    O execute(I input);
}
