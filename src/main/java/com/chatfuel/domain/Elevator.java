package com.chatfuel.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class Elevator {

    @Setter @Getter private int currentFloor;
    @Setter @Getter private Queue<Person> inside = new PriorityQueue<>(personComparator);
    @Setter @Getter private Queue<Person> outside = new PriorityQueue<>(personComparator);

    @Setter @Getter private boolean doorsOpened = false;
    @Setter @Getter private boolean underOperate = false;

    public Elevator(int floorTotal) {
        this.currentFloor = new Random().nextInt(floorTotal);
    }

    public void addPersonToQueueOutside(Person person){
        outside.add(person);
    }

    //Comparator anonymous class implementation
    private static Comparator<Person> personComparator = (p1, p2) -> (int) (p1.getCurrentFloor() - p2.getCurrentFloor());
}
