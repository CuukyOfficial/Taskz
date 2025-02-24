import de.cuuky.taskz.ScheduledExecutor;
import de.cuuky.taskz.observe.ObserverExecutor;
import de.cuuky.taskz.observe.OrderedObserverExecutor;

public class ScheduledExecTest {

    public static void main(String[] args) throws InterruptedException {
        ObserverExecutor<String> obs = new OrderedObserverExecutor<>();
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
