package com.chatfuel.domain;

import lombok.Getter;

public class Person {

    @Getter private Direction direction;
    @Getter private int currentFloor;
    @Getter private int desireFloor;

}
