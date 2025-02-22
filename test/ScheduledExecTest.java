import de.cuuky.taskz.ScheduledExecutor;
import de.cuuky.taskz.observe.ObserverManager;

public class ScheduledExecTest {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutor<String, String> t = new ScheduledExecutor<>((String s) -> s, 1000);
        ObserverManager<String> obs = t.execute(() -> String.valueOf(System.currentTimeMillis()));
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
