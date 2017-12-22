package com.chatfuel.domain;

import lombok.Getter;
import lombok.Setter;

public class Person {

    @Setter @Getter private boolean inProgress;
    @Getter private int currentFloor;
    @Getter private int desireFloor;
    @Setter @Getter private long timeStamp;

}
