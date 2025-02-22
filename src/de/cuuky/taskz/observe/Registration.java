package de.cuuky.taskz.observe;

import java.util.Objects;
import java.util.UUID;

public record Registration<T>(UUID uuid, Class<T> clazz, Observer<T> task,
                              int priority) implements Comparable<Registration<?>> {

    public Boolean execute(Object input) {
        return this.task.execute(this.clazz.cast(input));
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Registration<?> that = (Registration<?>) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int compareTo(Registration<?> o) {
        return this.priority == o.priority ? this.uuid.compareTo(o.uuid) : this.priority - o.priority;
    }
}
