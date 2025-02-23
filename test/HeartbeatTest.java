import de.cuuky.taskz.ScheduledExecutor;
import de.cuuky.taskz.observe.ObserverManager;
import de.cuuky.taskz.observe.OrderedObserverManager;

public class HeartbeatTest {

    public static void main(String[] args) {
        ObserverManager<Event> obs = new OrderedObserverManager<>();
        new ScheduledExecutor<>(obs, 20).execute(() -> new StateBeatEvent(State.RUNNING));

        obs.observe((state) -> {
            System.out.println(state);
            return true;
        });
    }
}

interface Event {

}

record StateBeatEvent(State state) implements Event {

}

enum State implements Event {

    IDLE,
    RUNNING,
    END

}
