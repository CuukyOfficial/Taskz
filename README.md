# Event-Driven Logic
Taskz aims to provide a simple, yet powerful API for Event-Driven Logic (EDL). Many business applications
can be divided into core features and addons based on these core features. Taskz makes it easy for developers
to make their addons as modules, by using events for communicating between core and addon. Furthermore,
on full-stack java applications this can be used for better and more abstract separation between UI and logic.

## Introduction
This is still WIP, but in a nutshell

- Taskz introduces a task abstraction for everything that needs to be done. Tasks have an input and an output, can be scheduled and delayed and chained together or only be executed on certain conditions.
- Taskz introduces a general-purpose observer pattern. It works like this:

```
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
```
