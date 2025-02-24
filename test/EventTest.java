import de.cuuky.taskz.observe.*;

class EventTest {

    public static void main(String[] args) {
        ObserverExecutor<Event> registry = new OrderedObserverExecutor<>();

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            registry.observe((a) -> {
                System.out.println(finalI);
                return true;
            });
        }

        registry.execute(new EventA("a"));
    }
}

record EventA(String s) implements Event {

}

//record EventB() implements Event {
//}


//class EventAObserver implements Observer<EventA> {
//
//    @Override
//    public Boolean execute(EventA input) {
//        System.out.println("EventAListener");
//        return null;
//    }
//}
//
//@ObserverMeta(value = ExecutionOrder.LOGIC)
//class EventBObserver implements Observer<EventB> {
//
//    @Override
//    public Boolean execute(EventB input) {
//        System.out.println("EventBListener");
//        return null;
//    }
//}
//
//@ObserverMeta(value = ExecutionOrder.LOGIC)
//class EventBObserver2 implements Observer<EventB> {
//
//    @Override
//    public Boolean execute(EventB input) {
//        System.out.println("EventBListener2");
//        return null;
//    }
//}