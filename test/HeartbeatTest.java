import de.cuuky.taskz.OptionalTask;
import de.cuuky.taskz.ScheduledExecutor;
import de.cuuky.taskz.StatefulTask;
import de.cuuky.taskz.SwitchingExecutor;
import de.cuuky.taskz.observe.ObserverManager;
import de.cuuky.taskz.observe.OrderedObserverManager;

public class HeartbeatTest {

    static State state = State.RUNNING;

    public static void main(String[] args) throws InterruptedException {
        ObserverManager<Event> obs = new OrderedObserverManager<>();
        new ScheduledExecutor<>(obs, 20).execute(() -> new StateBeatEvent(state));

//        obs.observe((state) -> {
//            System.out.println(state);
//            return true;
//        });

        OptionalTask<StateBeatEvent, String> opt = new SwitchingExecutor<>(new StatefulTask<>() {
            @Override
            public boolean cancel(boolean interrupt) {
                return true;
            }

            @Override
            public boolean isRunning() {
                return true;
            }

            @Override
            public String execute(StateBeatEvent input) {
                return "Start";
            }
        }, (e) -> e.state() == State.RUNNING);

        obs.observe((StateBeatEvent e) -> {
            System.out.println(opt.execute(e));
            return true;
        });

        Thread.sleep(5000);
        state = State.END;
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
