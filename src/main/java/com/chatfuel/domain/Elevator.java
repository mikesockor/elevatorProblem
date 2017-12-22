package com.chatfuel.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Elevator {

    @Setter @Getter private int currentFloor;
    @Setter @Getter private Queue<Person> queue;

    @Setter @Getter private boolean doorsOpened;
    @Setter @Getter private boolean underProgress;
    @Setter @Getter private Direction direction;

    public Elevator(int floorTotal, Comparator personComparator) {
        this.currentFloor = new Random().nextInt(floorTotal);
        this.queue = new PriorityQueue<>(personComparator);
    }

    public void addPersonToQueue(Person person){
        queue.add(person);
    }
}
