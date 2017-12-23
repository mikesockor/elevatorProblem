package com.chatfuel.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

public class Person {

    @Setter @Getter private boolean inProgress;
    @Getter private int currentFloor;
    @Getter private int desireFloor;
    @Setter @Getter private long timeStamp;

    public Direction getDirection(){
        return currentFloor>desireFloor ? Direction.DOWN : Direction.UP;
    }

    public Person(int currentFloor, int desireFloor) {
        this.currentFloor = currentFloor;
        this.desireFloor = desireFloor;
        this.timeStamp = Instant.now().toEpochMilli();
    }

    @Override
    public String toString() {
        return "Person{" +
                "inProgress=" + inProgress +
                ", currentFloor=" + currentFloor +
                ", desireFloor=" + desireFloor +
                '}';
    }

}
