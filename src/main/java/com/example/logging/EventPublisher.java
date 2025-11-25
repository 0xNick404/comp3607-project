package com.jeopardy.logging;

public interface EventPublisher {
    void registerListener(EventListener listener);

    void unregisterListener(EventListener listener);

    void publish(EventRecord event);
}