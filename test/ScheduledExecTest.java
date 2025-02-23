import de.cuuky.taskz.ScheduledExecutor;
import de.cuuky.taskz.observe.ObserverManager;
import de.cuuky.taskz.observe.OrderedObserverManager;

public class ScheduledExecTest {

    public static void main(String[] args) throws InterruptedException {
        ObserverManager<String> obs = new OrderedObserverManager<>();
        ScheduledExecutor<String> t = new ScheduledExecutor<>(obs, 1000);
        t.execute(() -> "Hallo");

        obs.observe((s) -> {
            System.out.println(s);
            return true;
        });

        obs.observe((s) -> {
            System.out.println("Me too!");
            return true;
        });

        Thread.sleep(5000);

        t.cancel(true);
    }
}
