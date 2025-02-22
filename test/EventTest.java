import de.cuuky.taskz.observe.Observer;
import de.cuuky.taskz.observe.ObserverManager;
import de.cuuky.taskz.observe.OrderedObserverManager;
import de.cuuky.taskz.observe.ObserverMeta;

class EventTest {

    public static void main(String[] args) {
        ObserverManager<Event> registry = new OrderedObserverManager<>();

        registry.observe((a) -> {
            System.out.println("everything");
            return null;
        });

        registry.observe((EventA a)-> {
            System.out.println("Just event A");
           return null;
        });

        registry.execute(new EventA("a"));
        registry.execute(new EventB());
    }
}

interface Event {
}

record EventA(String s) implements Event {

}

record EventB() implements Event {
}


class EventAObserver implements Observer<EventA> {

    @Override
    public Boolean execute(EventA input) {
        System.out.println("EventAListener");
        return null;
    }
}

@ObserverMeta(value = 1)
class EventBObserver implements Observer<EventB> {

    @Override
    public Boolean execute(EventB input) {
        System.out.println("EventBListener");
        return null;
    }
}

@ObserverMeta(value = 2)
class EventBObserver2 implements Observer<EventB> {

    @Override
    public Boolean execute(EventB input) {
        System.out.println("EventBListener2");
        return null;
    }
}