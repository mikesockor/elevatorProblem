package com.general;

import com.general.eventSystem.Event;
import com.general.eventSystem.EventDispatcher;
import com.general.eventSystem.EventListener;
import com.general.eventSystem.MessageListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GenericEventDispatcher implements EventDispatcher {

    private final Map<Class, List<EventListener<?>>> eventListeners;
    private final Map<Class, List<MessageListener>>  messageListeners;
    private final ExecutorService                    executor;

    @SuppressWarnings("unchecked") GenericEventDispatcher() {
        this.eventListeners = new HashMap();
        this.messageListeners = new HashMap();
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public <T extends Event> void addEventListener(Class<T> eventType, EventListener<T> listener) {

        eventListeners.computeIfAbsent(eventType, v -> Collections.singletonList(listener));
//        List<EventListener<?>> existed = eventListeners.getOrDefault(eventType, Collections.singletonList(listener));
//        eventListeners.put(eventType, existed);

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Event> void emitEventAsync(final T event) {
        // We guarantee that listener is capable of receiving its event type
        executor.execute(() ->
            eventListeners.get(event.getClass())
                .forEach(listener -> ((EventListener<T>) listener).onEvent(event))
        );
    }

    @Override
    public void addMessageListener(Class messageType, MessageListener listener) {
        List<MessageListener> existed = messageListeners.getOrDefault(messageType, Collections.singletonList(listener));
        messageListeners.put(messageType, existed);
    }

    @Override
    public <S extends String> void emitMessageAsync(S message) {
        // We guarantee that listener is capable of receiving its message
        executor.execute(() ->
            messageListeners.values()
                .forEach(listeners -> listeners.forEach(e -> e.onMessage(message)))
        );
    }
}
