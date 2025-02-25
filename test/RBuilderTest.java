import de.cuuky.taskz.RTaskBuilder;

import java.util.concurrent.ScheduledFuture;

public class RBuilderTest {

    static boolean check = true;

    public static void main(String[] args) {
        ScheduledFuture<?> t = new RTaskBuilder<>((a) -> {
            System.out.println("Input " + a);
            return true;
        }).check(
                (a) -> {
                    check = !check;
                    return check;
                }
        ).schedule(1000).execute(() -> "Hello");
    }
}
