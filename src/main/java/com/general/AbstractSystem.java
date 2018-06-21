package com.general;

import com.general.eventSystem.Event;
import com.general.eventSystem.EventDispatcher;
import com.general.eventSystem.EventListener;
import com.general.eventSystem.MessageListener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSystem<R extends AbstractElevator, E extends Event> implements EventListener<E>, MessageListener {

    private List<R>         elevators;
    private EventDispatcher dispatcher;

    public List<R> getElevators() {
        return elevators;
    }

    protected EventDispatcher getDispatcher() {
        return dispatcher;
    }

    protected void addElevator(R elevator) {
        this.elevators.add(elevator);
    }

    public AbstractSystem() {
        this.dispatcher = new GenericEventDispatcher();
        this.dispatcher.addMessageListener(AbstractSystem.class, this);
        this.elevators = new ArrayList<>();
    }

    @Override
    public void onEvent(E event) {
    }

    @Override
    public void onMessage(String message) {
    }
}
