package com.general;

import com.general.eventSystem.Event;
import com.general.eventSystem.EventDispatcher;
import com.general.eventSystem.EventListener;

import java.util.Queue;

public abstract class AbstractElevator<E extends Event, P> implements EventListener<E> {

    private Queue<E> eventQueue;
    private P property;
    private ElevatorStatus currentStatus;
    private int currentFloor = 0;
    private EventDispatcher dispatcher;

    public AbstractElevator(EventDispatcher dispatcher, Queue<E> eventQueue, P property) {
        this.dispatcher = dispatcher;
        this.eventQueue = eventQueue;
        this.property = property;
        this.currentStatus = ElevatorStatus.Hold;
    }

    // process queue
    public void process() {
    }

    protected final P getProperty() {
        return property;
    }

    protected final ElevatorStatus getCurrentStatus() {
        return currentStatus;
    }

    protected final void setCurrentStatus(ElevatorStatus currentStatus) {
        this.dispatcher.emitMessageAsync(String.format("Status %s", currentStatus.toString()));
        this.currentStatus = currentStatus;
    }

    protected final int getCurrentFloor() {
        return currentFloor;
    }

    protected final void setCurrentFloor(int currentFloor) {
        this.dispatcher.emitMessageAsync(String.format("Floor %d", currentFloor));
        this.currentFloor = currentFloor;
    }

    protected final Queue<E> getEventQueue() {
        return eventQueue;
    }

    public final EventDispatcher getDispatcher() {
        return dispatcher;
    }

    public static void sleep(int value) {
        try {
            Thread.sleep(value);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public synchronized void onEvent(E event) {
        dispatcher.emitMessageAsync(String.format("Dispatched event %s%n", event));
        if (!eventQueue.contains(event)) {
            eventQueue.add(event);
            process();
        }
    }
}
