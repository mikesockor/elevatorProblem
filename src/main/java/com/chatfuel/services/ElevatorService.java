package com.chatfuel.services;

import com.chatfuel.domain.Elevator;
import com.chatfuel.domain.Person;

import java.util.Comparator;
import java.util.List;

public interface ElevatorService {

    void elevatorInitiate();
    Elevator getApplicableElevator();
    void operateElevator(Elevator elevator);

    void setDoorsDelay(int doorsDelay);
    void setFloorHeight(double floorHeight);
    void setFloorTotal(int floorTotal);
    void setSpeed(int speed);
    void setElevatorNumber(int elevatorNumber);
    List<Elevator> getElevators();
    Comparator<Person> getPersonComparator();
}
