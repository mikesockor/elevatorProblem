package com.general.eventSystem;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
