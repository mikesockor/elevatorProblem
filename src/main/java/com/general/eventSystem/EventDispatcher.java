package com.general.eventSystem;

public interface EventDispatcher {

    /**
     * Registers event listener for particular event class
     */
    <T extends Event> void addEventListener(Class<T> eventType, EventListener<T> listener);

    /**
     * Emits the event asynchronously. .
     */
    <T extends Event> void emitEventAsync(T event);

    /**
     * Registers message listener for particular class
     */
    void addMessageListener(Class messageType, MessageListener listener);

    /**
     * Emits the message asynchronously. .
     */
    <S extends String> void emitMessageAsync(S message);

}
